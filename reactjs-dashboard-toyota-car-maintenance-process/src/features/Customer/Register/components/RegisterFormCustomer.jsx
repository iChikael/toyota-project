import React, { useEffect, useState } from 'react'

import {
  Box,
  Button,
  TextField,
  Select,
  InputBase,
  styled,
  MenuItem,
  InputLabel,
  Grid,
} from '@mui/material'
import { Formik } from 'formik'
import * as yup from 'yup'
import useMediaQuery from '@mui/material/useMediaQuery'
import Header from 'components/Header'
import { useDispatch, useSelector } from 'react-redux'
import {
  getAllProivces,
  getAllDistricts,
  getAllWards,
} from 'app/locationRegionSlice'

import { registerCustomer } from 'app/customerSlice'

import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer, toast } from 'react-toastify'

import CircularProgress from '@mui/material/CircularProgress'
import { useNavigate } from 'react-router-dom'
import Loading from 'components/Loading'
import { LoadingButton } from '@mui/lab'
import ToastUtils from 'utils/ToastUtils'
import BackListButton from 'components/Button/BackListButton'
import CloudUploadIcon from '@mui/icons-material/CloudUpload'

const BootstrapInput = styled(InputBase)(({ theme }) => ({
  'label + &': {
    marginTop: theme.spacing(3),
  },
  '& .MuiInputBase-input': {
    borderRadius: 4,
    position: 'relative',
    backgroundColor: theme.palette.background.paper,
    border: '1px solid #ced4da',
    fontSize: 16,
    padding: '10px 26px 10px 12px',
    transition: theme.transitions.create(['border-color', 'box-shadow']),
    // Use the system font instead of the default Roboto font.
    fontFamily: [
      '-apple-system',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      'Roboto',
      '"Helvetica Neue"',
      'Arial',
      'sans-serif',
      '"Apple Color Emoji"',
      '"Segoe UI Emoji"',
      '"Segoe UI Symbol"',
    ].join(','),
    '&:focus': {
      borderRadius: 4,
      borderColor: '#80bdff',
      boxShadow: '0 0 0 0.2rem rgba(0,123,255,.25)',
    },
  },
}))

const RegisterFormCustomer = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  // const auth = useSelector((state) => state.auth.data)

  const listProvice = useSelector(
    (state) => state.locationRegion.data.provinces
  )
  const listDistrict = useSelector(
    (state) => state.locationRegion.data.districts
  )
  const listWards = useSelector((state) => state.locationRegion.data.wards)

  const isNonMobile = useMediaQuery('(min-width:600px)')

  const [provinceId, setProvinceId] = useState('')
  const [provinceName, setProvinceName] = useState('')
  const [districtId, setDistrictId] = useState('')
  const [districtName, setDistrictName] = useState('')
  const [wardId, setWardId] = useState('')
  const [wardName, setWardName] = useState('')

  const getProvinces = () => {
    const getAllProvinceAction = getAllProivces({})
    dispatch(getAllProvinceAction)
      .unwrap()
      .then((data) => {
        setProvinceId(data.results[0].province_id)
      })
  }

  useEffect(() => {
    getProvinces()
  }, [])

  const getDistricts = (provinceId) => {
    if (provinceId) {
      const getAllDistrictAction = getAllDistricts({ id: provinceId })
      dispatch(getAllDistrictAction)
        .unwrap()
        .then((data) => {
          setDistrictId(data[0].district_id)
        })
    }
  }

  const [loading, setLoading] = useState(false)

  const getWards = (districtId) => {
    if (districtId) {
      const getAllWardAction = getAllWards({ id: districtId })
      dispatch(getAllWardAction)
        .unwrap()
        .then((data) => {
          setWardId(data[0].ward_id)
          setWardName(data[0].ward_name)
        })
    }
  }

  const handleChangeProvince = (event) => {
    setProvinceId(event.target.dataset.value)
    setProvinceName(event.target.dataset.name)
  }

  const handleChangeDistrict = (event) => {
    setDistrictId(event.target.dataset.value)
    setDistrictName(event.target.dataset.name)
  }

  const handleChangeWard = (e) => {
    setWardId(e.target.dataset.value)
    setWardName(e.target.dataset.name)
  }

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const navigateDone = () => {
    navigate('/customers')
  }

  const handleFormSubmit = (values) => {
    setLoading(true)
    const registerForm = {
      username: values.username,
      password: values.password,
      fullName: values.fullName,
      email: values.email,
      dob: values.dob,
      identificationNumber: values.identificationNumber,
      provinceId: provinceId,
      provinceName: provinceName,
      districtId: districtId,
      districtName: districtName,
      wardId: wardId,
      wardName: wardName,
      address: values.address,
      avatar: avatar.currentFile,
      identificationImageBefore: identificationImageBefore.currentFile,
      identificationImageAfter: identificationImageAfter.currentFile,
      userRoleCode: null,
    }
    const registerFormData = new FormData()

    Object.entries(registerForm).forEach(([key, value]) => {
      registerFormData.append(key, value)
    })
    const registerAction = registerCustomer(registerFormData)
    dispatch(registerAction)
      .unwrap()
      .then((data) => {
        ToastUtils.showToastSuccessMessage('Thêm mới nhân viên thành công!')
        debounce(navigateDone, 2000)
      })
      .catch((error) => {
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu form thêm mới khách hàng, vui lòng kiểm tra mạng'
          )
        } else {
          ToastUtils.showToastFailMessage(error.response.data.message)
        }
        setLoading(false)
      })
  }

  const [avatar, setAvatar] = useState({
    currentFile: null,
    previewImage: null,
  })

  const handleSelectFileAvatar = (e) => {
    setAvatar({
      ...setAvatar,
      currentFile: e.target.files[0],
      previewImage: URL.createObjectURL(e.target.files[0]),
    })
  }

  const [identificationImageBefore, setIdentificationImageBefore] = useState({
    currentFile: null,
    previewImage: null,
  })

  const handleSelectFileBefore = (e) => {
    let selectedFile = e.target.files[0]
    if (selectedFile) {
      setIdentificationImageBefore({
        ...identificationImageBefore,
        currentFile: selectedFile,
        previewImage: URL.createObjectURL(selectedFile),
      })
    }
  }

  const [identificationImageAfter, setIdentificationImageAfter] = useState({
    currentFile: null,
    previewImage: null,
  })

  const handleSelectFileAffter = (e) => {
    let selectedFile = e.target.files[0]
    if (selectedFile) {
      setIdentificationImageAfter({
        ...identificationImageAfter,
        currentFile: e.target.files[0],
        previewImage: URL.createObjectURL(e.target.files[0]),
      })
    }
  }

  const handleBack = () => {
    navigate('/customers')
  }

  useEffect(() => {
    getDistricts(provinceId)
  }, [provinceId])

  useEffect(() => {
    getWards(districtId)
  }, [districtId])

  return (
    <Box m="20px">
      <ToastContainer autoClose={true} />
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Tạo mới khách hàng" />
        <BackListButton text="Danh sách khách hàng" onClick={handleBack} />
      </Grid>

      <Box m="40px 0 0 0">
        <Formik
          onSubmit={handleFormSubmit}
          initialValues={initialValues}
          validationSchema={checkoutSchema}
        >
          {({
            values,
            errors,
            touched,
            handleBlur,
            handleChange,
            handleSubmit,
          }) => (
            <form onSubmit={handleSubmit}>
              <Box
                display="grid"
                gap="30px"
                gridTemplateColumns="repeat(4, minmax(0, 1fr))"
                sx={{
                  '& > div': { gridColumn: isNonMobile ? undefined : 'span 4' },
                }}
              >
                <TextField
                  fullWidth
                  type="text"
                  label="Số điện thoại"
                  // onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.username}
                  name="username"
                  error={!!touched.username && !!errors.username}
                  helperText={touched.username && errors.username}
                  sx={{ gridColumn: 'span 2' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <TextField
                  fullWidth
                  type="password"
                  label="Mật khẩu"
                  // onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.password}
                  name="password"
                  error={!!touched.password && !!errors.password}
                  helperText={touched.password && errors.password}
                  sx={{ gridColumn: 'span 2' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <TextField
                  fullWidth
                  type="text"
                  label="Tên đầy đủ"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.fullName}
                  name="fullName"
                  error={!!touched.fullName && !!errors.fullName}
                  helperText={touched.fullName && errors.fullName}
                  sx={{ gridColumn: 'span 2' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <TextField
                  fullWidth
                  type="text"
                  label="Email"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.email}
                  name="email"
                  error={!!touched.email && !!errors.email}
                  helperText={touched.email && errors.email}
                  sx={{ gridColumn: 'span 2' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <TextField
                  fullWidth
                  focused
                  type="date"
                  label="Ngày sinh"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.dob}
                  name="dob"
                  error={!!touched.dob && !!errors.dob}
                  helperText={touched.dob && errors.dob}
                  sx={{ gridColumn: 'span 2' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <TextField
                  fullWidth
                  type="text"
                  label="Số căn cước công dân"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.identificationNumber}
                  name="identificationNumber"
                  error={
                    !!touched.identificationNumber &&
                    !!errors.identificationNumber
                  }
                  helperText={
                    touched.identificationNumber && errors.identificationNumber
                  }
                  sx={{ gridColumn: 'span 2' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <Select
                  value={provinceId}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {listProvice.map((item) => {
                    return (
                      <MenuItem
                        key={item.province_id}
                        value={item.province_id}
                        data-name={item.province_name}
                        onClick={handleChangeProvince}
                      >
                        {item.province_name}
                      </MenuItem>
                    )
                  })}
                </Select>

                <Select
                  value={districtId}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {listDistrict.map((item) => {
                    return (
                      <MenuItem
                        key={item.district_id}
                        value={item.district_id}
                        data-name={item.district_name}
                        onClick={handleChangeDistrict}
                      >
                        {item.district_name}
                      </MenuItem>
                    )
                  })}
                </Select>

                <Select
                  value={wardId}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {listWards.map((item) => {
                    return (
                      <MenuItem
                        key={item.ward_id}
                        value={item.ward_id}
                        data-name={item.ward_name}
                        onClick={handleChangeWard}
                      >
                        {item.ward_name}
                      </MenuItem>
                    )
                  })}
                </Select>
                <TextField
                  fullWidth
                  type="text"
                  label="Địa chỉ"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.address}
                  name="address"
                  error={!!touched.address && !!errors.address}
                  helperText={touched.address && errors.address}
                  sx={{ gridColumn: 'span 2' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />

                <Box sx={{ gridColumn: 'span 2' }}>
                  <InputLabel>Ảnh đại diện</InputLabel>
                  <input
                    id="avatar-upload"
                    style={{ display: 'none' }}
                    type="file"
                    accept="image/*"
                    onChange={handleSelectFileAvatar}
                  />
                  <label htmlFor="avatar-upload">
                    <Button
                      variant="contained"
                      margin="normal"
                      component="span"
                      startIcon={<CloudUploadIcon />}
                      sx={{
                        backgroundColor: 'blue',
                        marginBottom: '15px',
                        marginTop: '15px',
                      }}
                    >
                      Upload a file
                    </Button>
                  </label>
                  <div>
                    <img
                      className="preview"
                      src={avatar.previewImage}
                      alt=""
                      width="200px"
                      height="100px"
                    />
                  </div>
                </Box>

                <Box sx={{ gridColumn: 'span 2' }}>
                  <InputLabel>Ảnh CCCD mặt trước</InputLabel>
                  <input
                    id="before-upload"
                    style={{ display: 'none' }}
                    type="file"
                    accept="image/*"
                    onChange={handleSelectFileBefore}
                  />
                  <label htmlFor="before-upload">
                    <Button
                      variant="contained"
                      margin="normal"
                      component="span"
                      startIcon={<CloudUploadIcon />}
                      sx={{
                        backgroundColor: 'blue',
                        marginBottom: '15px',
                        marginTop: '15px',
                      }}
                    >
                      Upload a file
                    </Button>
                  </label>
                  <div>
                    <img
                      className="preview"
                      src={identificationImageBefore.previewImage}
                      alt=""
                      width="200px"
                      height="100px"
                    />
                  </div>
                </Box>
                <Box sx={{ gridColumn: 'span 2' }}>
                  <InputLabel>Ảnh CCCD mặt sau</InputLabel>
                  <input
                    id="after-upload"
                    style={{ display: 'none' }}
                    type="file"
                    accept="image/*"
                    onChange={handleSelectFileAffter}
                  />
                  <label htmlFor="after-upload">
                    <Button
                      variant="contained"
                      margin="normal"
                      component="span"
                      startIcon={<CloudUploadIcon />}
                      sx={{
                        backgroundColor: 'blue',
                        marginBottom: '15px',
                        marginTop: '15px',
                      }}
                    >
                      Upload a file
                    </Button>
                  </label>
                  <div>
                    <img
                      className="preview"
                      src={identificationImageAfter.previewImage}
                      alt=""
                      width="200px"
                      height="100px"
                    />
                  </div>
                </Box>
              </Box>
              <Box display="flex" justifyContent="end" mt="20px">
                <LoadingButton
                  type="submit"
                  color="secondary"
                  loading={loading}
                  loadingPosition="start"
                  variant="contained"
                  sx={{
                    ml: '10px',
                    minWidth: '200px',
                    minHeight: '35px',
                    m: '20px 0 20px 0 ',
                  }}
                >
                  Khởi tạo khách hàng
                </LoadingButton>
              </Box>
            </form>
          )}
        </Formik>
      </Box>
    </Box>
  )
}

const checkoutSchema = yup.object().shape({
  username: yup
    .string()
    .matches(
      /^(\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$/,
      'Tên đăng nhập không hợp lệ, phải là SĐT bắt đầu từ 0 hoặc +84 và có 10 chữ số'
    )
    .required('Tên đăng nhập là bắt buộc'),
  password: yup
    .string()
    .matches(/^.{3,50}$/, 'Mật khẩu cần có từ 3 đến 50 ký tự')
    .required('Mật khẩu là bắt buộc'),
  fullName: yup.string().required('Yêu cầu nhập đầy đủ tên họ khách hàng'),
  email: yup
    .string()
    .email('Không đúng định dạng email')
    .matches(
      /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
      'Email không hợp lệ'
    )
    .required('Yêu cầu nhập email khách hàng'),
  identificationNumber: yup
    .string()
    .matches(/^[0-9]{12}$/, 'Số CCCD phải đủ 12 chữ số')
    .required('Vui lòng nhập căn cước công dân của khách hàng'),
  address: yup.string().required('Vui lòng nhập địa chỉ khách hàng'),
  dob: yup.string().required('Yêu cầu nhập ngày tháng năm sinh'),
})

const initialValues = {
  username: '',
  password: '',
  fullName: '',
  email: '',
  dob: '',
  identificationNumber: '',
  address: '',
}

export default RegisterFormCustomer

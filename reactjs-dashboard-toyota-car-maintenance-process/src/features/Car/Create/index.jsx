import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import { ToastContainer } from 'react-toastify'

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
import { Formik, Field } from 'formik'
import * as yup from 'yup'
import useMediaQuery from '@mui/material/useMediaQuery'
import Header from 'components/Header'
import { createCar } from 'app/carSlice'
import { EFuel } from 'constants/EFuel'
import { EOrigin } from 'constants/EOrigin'
import { ESeat } from 'constants/ESeat'
import { getAllVehicles } from 'app/vehicleSlice'
import ImageInput from './components/ImageInput'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import CircularProgress from '@mui/material/CircularProgress'
import ToastUtils from 'utils/ToastUtils'
import Loading from 'components/Loading'
import BackListButton from 'components/Button/BackListButton'
import { LoadingButton } from '@mui/lab'

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

const CarCreatePage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const isNonMobile = useMediaQuery('(min-width:600px)')

  const [EnumFuel, setEnumFuel] = useState([])
  const [EnumOrigin, setEnumOrigin] = useState([])
  const [EnumSeat, setEnumSeat] = useState([])
  const [EFuelStatus, setEFuelStatus] = useState('')
  const [EOriginStatus, setEOriginStatus] = useState('')
  const [ESeatStatus, setESeatStatus] = useState('')
  const [listVehicle, setListVehicle] = useState([])
  const [vehicleId, setVehicleId] = useState('')

  const getEnumUnit = () => {
    const enumFuelValuesArray = Object.values(EFuel)
    const enumOriginValuesArray = Object.values(EOrigin)
    const enumSeatValuesArray = Object.values(ESeat)
    setEnumFuel(enumFuelValuesArray)
    setEnumOrigin(enumOriginValuesArray)
    setEnumSeat(enumSeatValuesArray)
    setEFuelStatus(EFuel.FUEL_OIL.statusValue)
    setEOriginStatus(EOrigin.ORIGIN_VN.statusValue)
    setESeatStatus(ESeat.SEAT_FIVE.statusValue)
  }

  const [loadingPage, setLoadingPage] = useState(false)

  const getVehicle = () => {
    const getAllVehiclesAction = getAllVehicles({})
    dispatch(getAllVehiclesAction)
      .unwrap()
      .then((data) => {
        setListVehicle(data)
        setVehicleId(data[0].id)
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu dòng xe, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoadingPage(false)
      })
  }

  const handleChangeVehicle = (event) => {
    setVehicleId(event.target.value)
  }

  useEffect(() => {
    getVehicle()
    getEnumUnit()
  }, [])

  const handleFormSubmit = async (values) => {
    setLoading(true)
    const enumOriginSelect = EOriginStatus
    const enumFuelSelect = EFuelStatus
    const enumSeatSelect = ESeatStatus
    const vehicleIdSelect = vehicleId
    const registerForm = {
      code: values.code,
      title: values.title,
      price: values.price,
      description: values.description,
      seatCode: enumSeatSelect,
      fuelCode: enumFuelSelect,
      originCode: enumOriginSelect,
      vehicleId: vehicleIdSelect,
      carAvatar: values.avatar,
    }

    const registerFormData = new FormData()

    Object.entries(registerForm).forEach(([key, value]) => {
      registerFormData.append(key, value)
    })
    const registerAction = createCar(registerFormData)
    dispatch(registerAction)
      .unwrap()
      .then((data) => {
        setLoading(false)
        ToastUtils.showToastSuccessMessage('Thêm mới xe thành công!')
      })
      .catch((error) => {
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu form thêm mới xe, vui lòng kiểm tra mạng'
          )
        } else {
          ToastUtils.showToastFailMessage(error.message)
        }
        setLoading(false)
      })
  }

  const [loading, setLoading] = useState(false)

  const [avatar, setAvatar] = useState({
    currentFile: null,
    previewImage: null,
  })

  const handleSelectFileAvatar = (e) => {
    const selectedFile = e.target.files[0]
    if (selectedFile) {
      setAvatar({
        ...avatar,
        currentFile: selectedFile,
        previewImage: URL.createObjectURL(selectedFile),
      })
    }
  }

  const handleChangeEnumFuelStatus = (event) => {
    setEFuelStatus(event.target.value)
  }
  const handleChangeEnumOriginStatus = (event) => {
    setEOriginStatus(event.target.value)
  }
  const handleChangeEnumSeatStatus = (event) => {
    setESeatStatus(event.target.value)
  }

  const handleBack = () => {
    navigate(-1)
  }

  return (
    <Box m="20px" pb="20px">
      <ToastContainer autoClose={true} />
      {loadingPage && <Loading />}
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Tạo mới xe" />
        <BackListButton text="Danh sách xe" onClick={handleBack} />
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
            setFieldValue,
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
                  label="Tên xe"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.title}
                  name="title"
                  error={!!touched.title && !!errors.title}
                  helperText={touched.title && errors.title}
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
                  label="CODE"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.code}
                  name="code"
                  error={!!touched.code && !!errors.code}
                  helperText={touched.code && errors.code}
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
                  label="Giá"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.price}
                  name="price"
                  error={!!touched.price && !!errors.price}
                  helperText={touched.price && errors.price}
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
                  label="Thông tin xe"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.description}
                  name="description"
                  error={!!touched.description && !!errors.description}
                  helperText={touched.description && errors.description}
                  sx={{ gridColumn: 'span 2' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <Select
                  fullWidth
                  type="text"
                  label="Loại hình xe"
                  value={vehicleId}
                  onChange={handleChangeVehicle}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {listVehicle.map((vehicle) => {
                    return (
                      <MenuItem key={vehicle.id} value={vehicle.id}>
                        {vehicle.name}
                      </MenuItem>
                    )
                  })}
                </Select>
                <Select
                  fullWidth
                  type="text"
                  label="Số chỗ ngồi"
                  value={ESeatStatus}
                  onChange={handleChangeEnumSeatStatus}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {EnumSeat.map((enumUnit) => {
                    return (
                      <MenuItem
                        key={enumUnit.status}
                        value={enumUnit.statusValue}
                      >
                        {enumUnit.statusValue}
                      </MenuItem>
                    )
                  })}
                </Select>
                <Select
                  fullWidth
                  variant="filled"
                  type="text"
                  label="Dòng xe"
                  value={EOriginStatus}
                  onChange={handleChangeEnumOriginStatus}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {EnumOrigin.map((enumUnit) => {
                    return (
                      <MenuItem
                        key={enumUnit.status}
                        value={enumUnit.statusValue}
                      >
                        {enumUnit.statusValue}
                      </MenuItem>
                    )
                  })}
                </Select>
                <Select
                  fullWidth
                  variant="filled"
                  type="text"
                  label="Tiêu thụ nguyên liệu"
                  value={EFuelStatus}
                  onChange={handleChangeEnumFuelStatus}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {EnumFuel.map((enumUnit) => {
                    return (
                      <MenuItem
                        key={enumUnit.status}
                        value={enumUnit.statusValue}
                      >
                        {enumUnit.statusValue}
                      </MenuItem>
                    )
                  })}
                </Select>
                <Box sx={{ gridColumn: 'span 2' }}>
                  <InputLabel>Ảnh mô tả xe</InputLabel>
                  <Field
                    name="avatar"
                    component={ImageInput}
                    title="Avatar"
                    setFieldValue={setFieldValue}
                    errorMessage={
                      errors.marker_video ? errors.marker_video : ''
                    }
                    touched={touched.marker_video}
                    onBlur={handleBlur}
                    onChangeCustom={handleSelectFileAvatar}
                  />
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
                    minWidth: '150px',
                    minHeight: '35px',
                    m: '20px 0 20px 0 ',
                  }}
                >
                  Tạo mới xe
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
  code: yup.string().required('Vui lòng nhập mã code của xe'),
  title: yup.string().required('Vui lòng nhập tên xe'),
  price: yup.string().required('Vui lòng nhập giá'),
  description: yup.string().required('Vui lòng nhập thông tin xe'),
  avatar: yup.mixed().required('Vui lòng thêm ảnh mô tả'),
})

const initialValues = {
  code: '',
  title: '',
  price: '',
  description: '',
  seatCode: '',
  fuelCode: '',
  originCode: '',
  vehicleId: '',
  avatar: null,
}

export default CarCreatePage

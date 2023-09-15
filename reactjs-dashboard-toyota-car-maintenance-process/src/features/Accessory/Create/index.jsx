import { Box, Button, Grid, TextField } from '@mui/material'
import { Formik } from 'formik'
import * as yup from 'yup'
import useMediaQuery from '@mui/material/useMediaQuery'
import Header from 'components/Header'
import { useDispatch } from 'react-redux'
import { getAllAccessoryRole, createAccessory } from 'app/accessoriesSlice'
import { useEffect, useState } from 'react'
import MenuItem from '@mui/material/MenuItem'
import Select from '@mui/material/Select'
import InputBase from '@mui/material/InputBase'
import { styled } from '@mui/material/styles'
import { EnumUnit } from 'constants/EnumUnit'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer } from 'react-toastify'
import ToastUtils from 'utils/ToastUtils'
import { useNavigate } from 'react-router-dom'
import BackListButton from 'components/Button/BackListButton'

const CreateAccessoryPage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const [accessoryRoleId, setAccessoryRoleId] = useState('')
  const [Enum, setEnum] = useState([])
  const [EnumStatus, setEnumStatus] = useState('')

  const [listAccessoryRole, setListAccessoryRole] = useState([])

  const getAccessoryRole = () => {
    const getAllAccessoryRoleAction = getAllAccessoryRole({})
    dispatch(getAllAccessoryRoleAction)
      .unwrap()
      .then((data) => {
        setListAccessoryRole(data)
        setAccessoryRoleId(data[0].id)
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu dòng xe, vui lòng kiểm tra mạng'
          )
        }
      })
  }

  const [createAccessoryFrom, setCreateAccessoryForm] = useState({
    name: '',
    code: '',
    quantity: '',
    price: '',
    unit: '',
    accessoryRole: '',
  })

  const getEnumUnit = () => {
    const enumValuesArray = Object.values(EnumUnit)
    setEnum(enumValuesArray)
    setEnumStatus(EnumUnit.UNIT_BOTTLE.statusValue)
  }

  useEffect(() => {
    getAccessoryRole()
    getEnumUnit()
  }, [])

  const isNonMobile = useMediaQuery('(min-width:600px)')

  const BootstrapInput = styled(InputBase)(({ theme }) => ({
    'label + &': {
      marginTop: theme.spacing(3),
    },
    '& .MuiInputBase-input': {
      borderRadius: 4,
      position: 'relative',
      backgroundColor: theme.palette.background.paper,
      border: '1px solid #CED4DA',
      fontSize: 16,
      padding: '10px 26px 10px 12px',
      transition: theme.transitions.create(['border-color', 'box-shadow']),
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
        borderColor: '#80BDFF',
        boxShadow: '0 0 0 0.2rem rgba(0,123,255,.25)',
      },
    },
  }))

  const handleChangeAccessoryRole = (event) => {
    setAccessoryRoleId(event.target.value)
  }

  const handleFormSubmit = (values) => {
    const enumStatusSelect = EnumStatus
    const accessoryRoleIdSelect = accessoryRoleId

    const createForm = {
      name: values.name,
      code: values.code,
      quantity: values.quantity,
      price: values.price,
      unit: enumStatusSelect,
      accessoryRole: accessoryRoleIdSelect,
    }
    setCreateAccessoryForm(createForm)
    const createAccessoryAction = createAccessory(createForm)
    dispatch(createAccessoryAction)
      .unwrap()
      .then((data) => {
        ToastUtils.showToastSuccessMessage('Thêm mới linh kiện thành công!')
      })
      .catch((error) => {
        ToastUtils.showToastFailMessage(error.message)
      })
  }

  const handleChangeEnumStatus = (event) => {
    setEnumStatus(event.target.value)
  }

  const handleBack = () => {
    navigate('/accessories')
  }

  return (
    <Box m="20px" pb="20px">
      <ToastContainer autoClose={true} />

      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Tạo mới linh kiện - vật tư" />
        <BackListButton
          text="Danh sách linh kiện - vật tư"
          onClick={handleBack}
        />
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
                  label="Tên linh kiện - vật tư"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.name}
                  name="name"
                  error={!!touched.name && !!errors.name}
                  helperText={touched.name && errors.name}
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
                  label="Mã CODE"
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
                  sx={{ gridColumn: 'span 4' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <TextField
                  fullWidth
                  type="text"
                  label="Số lượng nhập"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.quantity}
                  name="quantity"
                  error={!!touched.quantity && !!errors.quantity}
                  helperText={touched.quantity && errors.quantity}
                  sx={{ gridColumn: 'span 4' }}
                  InputLabelProps={{
                    sx: {
                      backgroundColor: 'white',
                    },
                  }}
                />
                <Select
                  fullWidth
                  type="text"
                  label="Loại linh kiện - vật tư"
                  value={accessoryRoleId}
                  onChange={handleChangeAccessoryRole}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {listAccessoryRole.map((accessoryRole) => {
                    return (
                      <MenuItem key={accessoryRole.id} value={accessoryRole.id}>
                        {accessoryRole.code}
                      </MenuItem>
                    )
                  })}
                </Select>
                <Select
                  fullWidth
                  type="text"
                  label="Đơn vị tính"
                  value={EnumStatus}
                  onChange={handleChangeEnumStatus}
                  input={<BootstrapInput />}
                  sx={{ gridColumn: 'span 2' }}
                >
                  {Enum.map((enumUnit) => {
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
              </Box>
              <Box display="flex" justifyContent="end" mt="20px">
                <Button type="submit" color="secondary" variant="contained">
                  Tạo mới
                </Button>
              </Box>
            </form>
          )}
        </Formik>
      </Box>
    </Box>
  )
}

const checkoutSchema = yup.object().shape({
  name: yup.string().required('Yêu cầu nhập tên linh kiện'),
  code: yup.string().required('Yêu cầu nhập code của linh kiện'),
  quantity: yup.string().required('Vui lòng nhập số lượng'),
  price: yup.string().required('Vui lòng nhập giá'),
})

const initialValues = {
  name: '',
  code: '',
  quantity: '',
  price: '',
  unit: '',
  accessoryRole: '',
}

export default CreateAccessoryPage

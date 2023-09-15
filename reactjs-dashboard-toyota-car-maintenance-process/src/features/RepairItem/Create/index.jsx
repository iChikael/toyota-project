import { Box, Button, TextField } from '@mui/material'
import { Formik } from 'formik'
// import * as yup from 'yup'
import useMediaQuery from '@mui/material/useMediaQuery'
import Header from 'components/Header'
import { useDispatch } from 'react-redux'
import { useEffect, useState } from 'react'
import InputBase from '@mui/material/InputBase'
import { styled } from '@mui/material/styles'
import { createRepairItem } from 'app/repairItemSlice'
import { getAllServiceArea } from 'app/serviceAreaSlice'
import MenuItem from '@mui/material/MenuItem'
import Select from '@mui/material/Select'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer, toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom'
import ToastUtils from 'utils/ToastUtils'
import BackButton from 'components/Button/BackButton'

const RepairItemCreatePage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const [serviceAreaId, setserviceAreaId] = useState('')

  const [createRepairFrom, setcreateRepairFrom] = useState({
    title: '',
    serviceArea: '',
  })

  const [listServiceArea, setListServiceArea] = useState([])

  const handleGetAllServiceArea = () => {
    dispatch(getAllServiceArea())
      .unwrap()
      .then((data) => {
        setListServiceArea(data)
        setserviceAreaId(data[0].id)
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách hạng mục sửa chữa, vui lòng kiểm tra mạng'
          )
        }
      })
  }

  const handleChangeServiceArea = (event) => {
    setserviceAreaId(event.target.value)
  }

  useEffect(() => {
    handleGetAllServiceArea()
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

  const handleFormSubmit = (values) => {
    const serviceAreaIdSelect = serviceAreaId

    const createForm = {
      title: values.title,
      serviceArea: serviceAreaIdSelect,
    }
    setcreateRepairFrom(createForm)
    const createRepairItemAction = createRepairItem(createForm)
    dispatch(createRepairItemAction)
      .unwrap()
      .then((data) => {
        ToastUtils.showToastSuccessMessage('Thêm mới dịch vụ thành công!')
      })
      .catch((error) => {
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu thêm mới hạng mục, vui lòng kiểm tra mạng'
          )
        } else {
          ToastUtils.showToastFailMessage(
            'Thêm mới dịch vụ thất bại. Vui lòng kiểm tra lại thông tin!'
          )
        }
      })
  }

  const handleBackListRepairItem = () => {
    navigate(-1)
  }

  return (
    <Box m="20px" pb="20px">
      <ToastContainer autoClose={true} />
      <Header title="Tạo mới hạng mục sửa chữa" />
      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        // validationSchema={checkoutSchema}
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
                variant="filled"
                type="text"
                label="Hạng mục sửa chữa"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.title}
                name="title"
                error={!!touched.title && !!errors.title}
                helperText={touched.title && errors.title}
                sx={{ gridColumn: 'span 2' }}
              />
              <Select
                fullWidth
                variant="filled"
                type="text"
                label="Khu vực sửa chữa"
                value={serviceAreaId}
                onChange={handleChangeServiceArea}
                input={<BootstrapInput />}
                sx={{ gridColumn: 'span 2' }}
              >
                {listServiceArea.map((serviceArea) => {
                  return (
                    <MenuItem key={serviceArea.id} value={serviceArea.id}>
                      {serviceArea.name}
                    </MenuItem>
                  )
                })}
              </Select>
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <BackButton onClick={handleBackListRepairItem} />

              <Button
                type="submit"
                color="secondary"
                variant="contained"
                sx={{ ml: '10px', mt: '10px' }}
              >
                Tạo mới
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  )
}

const initialValues = {
  carNumberPlates: '',
  fullName: '',
  phone: '',
}

export default RepairItemCreatePage

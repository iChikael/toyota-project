import { Box, Button, TextField } from '@mui/material'
import { Formik } from 'formik'
import * as yup from 'yup'
import useMediaQuery from '@mui/material/useMediaQuery'
import Header from 'components/Header'
import { useDispatch } from 'react-redux'
import { useEffect, useState } from 'react'
import InputBase from '@mui/material/InputBase'
import { styled } from '@mui/material/styles'
import { createServiceArea } from 'app/serviceAreaSlice'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer, toast } from 'react-toastify'

const ServiceAreaCreatePage = () => {
  const dispatch = useDispatch()

  const [createServiceAreaForm, setcreateServiceAreaForm] = useState({
    name: '',
    capacity: '',
  })

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
    const createForm = {
      name: values.name,
      capacity: values.capacity,
    }
    setcreateServiceAreaForm(createForm)
    const createServiceAreaAction = createServiceArea(createForm)
    dispatch(createServiceAreaAction)
      .unwrap()
      .then((data) => {
        console.log('123')
        showToastMessage()
      })
      .catch((error) => {
        // console.log(error)
      })
  }

  const showToastMessage = () => {
    toast.success('Thêm mới khu vực thành công!', {
      position: toast.POSITION.TOP_RIGHT,
      autoClose: 1500,
      hideProgressBar: false,
      closeOnClick: true,
      draggable: false,
      pauseOnHover: true,
      width: '700px',
      style: {
        fontSize: '16px',
      },
      icon: true,
      closeButton: false,
    })
  }

  return (
    <Box m="20px" pb="20px">
      <ToastContainer autoClose={true} />
      <Header title="Tạo mới khu vực sửa chữa" />
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
                variant="filled"
                type="text"
                label="Khu vực sửa chữa"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.name}
                name="name"
                error={!!touched.name && !!errors.name}
                helperText={touched.name && errors.name}
                sx={{ gridColumn: 'span 2' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Sức chứa"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.capacity}
                name="capacity"
                error={!!touched.capacity && !!errors.capacity}
                helperText={touched.capacity && errors.capacity}
                sx={{ gridColumn: 'span 2' }}
              />
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
  )
}

const checkoutSchema = yup.object().shape({
  name: yup.string().required('Yêu cầu nhập tên khu vực'),
  capacity: yup.string().required('Yêu cầu nhập sức chứa của khu vực'),
})

const initialValues = {
  name: '',
  capacity: '',
}

export default ServiceAreaCreatePage

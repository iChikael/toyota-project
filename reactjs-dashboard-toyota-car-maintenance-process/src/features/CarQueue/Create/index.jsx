import { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'

import LoadingButton from '@mui/lab/LoadingButton'
import { Box, Button, Grid, TextField } from '@mui/material'
import useMediaQuery from '@mui/material/useMediaQuery'
import Header from 'components/Header'

import { createCarQueue } from 'app/carQueueSlice'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'

import { Formik } from 'formik'
import * as yup from 'yup'
import Helper from 'utils/Helper'
import ToastUtils from 'utils/ToastUtils'

const CreateCarQueuePage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const isNonMobile = useMediaQuery('(min-width:600px)')

  const carPlateById = useSelector((state) => state.reception.data.carPlateById)
  console.log(carPlateById)

  const [loading, setLoading] = useState(false)

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const navigateDone = () => {
    navigate('/reception/car-queues')
  }

  const handleFormSubmit = (values) => {
    setLoading(true)
    if (carPlateById) {
      const createCarQueueAction = createCarQueue({
        carNumberPlates: carPlateById.carPlate,
        fullName: values.fullName,
        phone: values.phone,
      })
      dispatch(createCarQueueAction)
        .unwrap()
        .then((data) => {
          ToastUtils.showToastSuccessMessage('Thêm mới xe hàng chờ thành công!')
          debounce(navigateDone, 2000)
        })
        .catch((error) => {
          console.log(error)
          ToastUtils.showToastFailMessage(error.response.data.message)
          setLoading(false)
        })
    } else {
      ToastUtils.showToastFailMessage('Vui lòng nhập đầy đủ thông tin!')
    }
  }

  const handleBackListCarPlate = () => {
    navigate(-1)
  }

  const handleBackListCarQueue = () => {
    navigate('/reception/car-queues')
  }

  return (
    <Box m="20px" pb="20px">
      <ToastContainer autoClose={true} />

      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Tạo mới xe hàng đợi" />
        <Box>
          <Button
            variant="contained"
            type="button"
            onClick={handleBackListCarPlate}
            color="secondary"
            sx={{ mb: '20px' }}
          >
            Quản lý biển số xe
          </Button>
          <Button
            variant="contained"
            type="button"
            onClick={handleBackListCarQueue}
            color="secondary"
            sx={{ ml: '10px', mb: '20px' }}
          >
            Danh sách xe hàng chờ
          </Button>
        </Box>
      </Grid>

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
                type="text"
                variant="outlined"
                value={
                  (carPlateById &&
                    carPlateById.carPlate &&
                    Helper.formatCarNumberPlate(carPlateById.carPlate)) ||
                  ''
                }
                InputProps={{
                  readOnly: true,
                }}
                sx={{ gridColumn: 'span 2' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Số điện thoại liên hệ"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.phone}
                name="phone"
                error={!!touched.phone && !!errors.phone}
                helperText={touched.phone && errors.phone}
                sx={{ gridColumn: 'span 2' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Tên khách hàng"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.fullName}
                name="fullName"
                error={!!touched.fullName && !!errors.fullName}
                helperText={touched.fullName && errors.fullName}
                sx={{ gridColumn: 'span 4' }}
              />
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <LoadingButton
                type="submit"
                color="secondary"
                loading={loading}
                loadingPosition="start"
                variant="contained"
                sx={{ ml: '10px', minWidth: '120px', minHeight: '35px' }}
              >
                Tạo mới
              </LoadingButton>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  )
}

const checkoutSchema = yup.object().shape({
  phone: yup
    .string()
    .matches(
      /^(\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$/,
      'Số điện thoại không hợp lệ, phải là SĐT bắt đầu từ 0 hoặc +84 và có 10 chữ số'
    )
    .required('Số điện thoại là bắt buộc'),
  fullName: yup.string().required('Yêu cầu nhập đầy đủ tên họ khách hàng'),
})

const initialValues = {
  fullName: '',
  phone: '',
}

export default CreateCarQueuePage

import React from 'react'
import { getAllCarQueue, updateCarQueueById } from 'app/carQueueSlice'
import { useDispatch, useSelector } from 'react-redux'
import { useEffect, useState } from 'react'
import { Formik } from 'formik'
import { Box, Button, Fade, Modal, TextField } from '@mui/material'
import useMediaQuery from '@mui/material/useMediaQuery'
import * as yup from 'yup'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer } from 'react-toastify'
import Helper from 'utils/Helper'
import ToastUtils from 'utils/ToastUtils'

const customStyles = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: '800px',
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
}

const ModalUpdateCarQueue = ({ modalIsOpen, closeModal }) => {
  const dispatch = useDispatch()
  const carQueue = useSelector((state) => state.carQueue.data.carQueue)

  const [updateCarQueueFrom, setUpdateCarQueueForm] = useState({
    id: carQueue.id,
    carNumberPlates: carQueue.carNumberPlates,
    fullName: carQueue.fullName,
    phone: carQueue.phone,
    status: carQueue.status,
  })

  useEffect(() => {
    setUpdateCarQueueForm({
      id: carQueue.id,
      carNumberPlates: carQueue.carNumberPlates,
      fullName: carQueue.fullName,
      phone: carQueue.phone,
    })
  }, [carQueue])

  const isNonMobile = useMediaQuery('(min-width:600px)')

  const handleFormSubmit = (values) => {
    const createForm = {
      id: carQueue.id,
      carNumberPlates: values.carNumberPlates,
      fullName: values.fullName,
      phone: values.phone,
      status: carQueue.status,
    }
    const formData = new FormData()
    for (const key in createForm) {
      formData.append(key, createForm[key])
    }

    setUpdateCarQueueForm(createForm)
    const updateCarQueueAction = updateCarQueueById(createForm)
    dispatch(updateCarQueueAction)
      .unwrap()
      .then((data) => {
        dispatch(getAllCarQueue())
        ToastUtils.showToastSuccessMessage('Cập nhật xe chờ thành công!')
        closeModal()
      })
      .catch((error) => {
        ToastUtils.showToastFailMessage(error.message)
      })
  }

  return (
    <div>
      <ToastContainer autoClose={true} />
      {modalIsOpen && (
        <Modal
          open={modalIsOpen}
          closemodal={closeModal}
          aria-label="Cập nhật thông tin xe chờ"
        >
          <Fade in={modalIsOpen}>
            <Box sx={customStyles}>
              <h1 className="text-center brand-color">
                Cập nhật thông tin xe chờ
              </h1>
              <Formik
                onSubmit={handleFormSubmit}
                initialValues={updateCarQueueFrom}
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
                        '& > div': {
                          gridColumn: isNonMobile ? undefined : 'span 4',
                        },
                      }}
                    >
                      <TextField
                        fullWidth
                        type="text"
                        label="Tên khách hàng"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.fullName}
                        name="fullName"
                        error={!!touched.fullName && !!errors.fullName}
                        helperText={touched.fullName && errors.fullName}
                        sx={{ gridColumn: 'span 3' }}
                        InputLabelProps={{
                          sx: {
                            backgroundColor: 'white',
                          },
                        }}
                      />
                      <TextField
                        fullWidth
                        type="text"
                        label="Biển số xe"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={
                          values.carNumberPlates &&
                          Helper.formatCarNumberPlate(values.carNumberPlates)
                        }
                        name="carNumberPlates"
                        error={
                          !!touched.carNumberPlates && !!errors.carNumberPlates
                        }
                        helperText={
                          touched.carNumberPlates && errors.carNumberPlates
                        }
                        sx={{ gridColumn: 'span 1' }}
                        InputLabelProps={{
                          sx: {
                            backgroundColor: 'white',
                          },
                        }}
                        InputProps={{
                          readOnly: true,
                        }}
                      />
                      <TextField
                        fullWidth
                        type="text"
                        label="Số điện thoại"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.phone}
                        name="phone"
                        error={!!touched.phone && !!errors.phone}
                        helperText={touched.phone && errors.phone}
                        sx={{ gridColumn: 'span 2' }}
                        InputLabelProps={{
                          sx: {
                            backgroundColor: 'white',
                          },
                        }}
                      />
                    </Box>
                    <Box display="flex" justifyContent="end" mt="35px">
                      <Button onClick={closeModal} className="close-button">
                        Đóng
                      </Button>
                      <Button
                        type="submit"
                        color="secondary"
                        variant="contained"
                        sx={{ ml: '10px' }}
                      >
                        Cập nhật
                      </Button>
                    </Box>
                  </form>
                )}
              </Formik>
            </Box>
          </Fade>
        </Modal>
      )}
    </div>
  )
}

const checkoutSchema = yup.object().shape({
  carNumberPlates: yup.string().required('Yêu cầu nhập tên linh kiện'),
  phone: yup.string().required('Yêu cầu nhập code của linh kiện'),
  fullName: yup.string().required('Vui lòng nhập số lượng'),
})

export default ModalUpdateCarQueue

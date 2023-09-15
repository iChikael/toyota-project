import React, { useReducer } from 'react'
import Select from '@mui/material/Select'
import MenuItem from '@mui/material/MenuItem'
import { updateServiceAreaById } from 'app/serviceAreaSlice'
import { useDispatch, useSelector } from 'react-redux'
import InputBase from '@mui/material/InputBase'
import { useEffect, useState } from 'react'
import { styled } from '@mui/material/styles'
import { Formik } from 'formik'
import { Box, Button, Fade, Modal, TextField } from '@mui/material'
import useMediaQuery from '@mui/material/useMediaQuery'
import * as yup from 'yup'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer, toast } from 'react-toastify'
import { EArenaStatus } from 'constants/EArenaStatus'

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

const ModalUpdateArea = ({ modalIsOpen, closeModal }) => {
  const dispatch = useDispatch()

  const serviceArea = useSelector((state) => state.serviceArea.data.serviceArea)

  const showToastFailMessage = (errorMessage) => {
    toast.error(`${errorMessage}!`, {
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

  const [updateAreaFrom, setUpdateAreaForm] = useState({
    id: serviceArea.id,
    name: serviceArea.name,
    capacity: serviceArea.capacity,
  })

  useEffect(() => {
    setUpdateAreaForm({
      id: serviceArea.id,
      name: serviceArea.name,
      capacity: serviceArea.capacity,
    })
  }, [serviceArea])

  const isNonMobile = useMediaQuery('(min-width:600px)')

  const showToastMessage = () => {
    toast.success('Cập nhật thông tin khu vực thành công!', {
      position: toast.POSITION.TOP_RIGHT,
      autoClose: 1000,
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

  const handleFormSubmit = (values) => {
    const createForm = {
      id: serviceArea.id,
      name: values.name,
      capacity: values.capacity,
      currentCapacity: serviceArea.currentCapacity,
      status: EArenaStatus.STATUS_FREE,
    }
    const formData = new FormData()
    for (const key in createForm) {
      formData.append(key, createForm[key])
    }
    setUpdateAreaForm(createForm)
    const updateAreaAction = updateServiceAreaById(createForm)
    dispatch(updateAreaAction)
      .unwrap()
      .then((data) => {
        showToastMessage()
        closeModal()
      })
      .catch((error) => {
        showToastFailMessage(error.message)
      })
  }

  return (
    <div>
      <ToastContainer autoClose={true} />
      {modalIsOpen && (
        <Modal
          open={modalIsOpen}
          closemodal={closeModal}
          aria-label="Cập nhật thông tin khu vực"
        >
          <Fade in={modalIsOpen}>
            <Box sx={customStyles}>
              <h1 className="text-center brand-color">
                Cập nhật thông tin khu vực
              </h1>
              <Formik
                onSubmit={handleFormSubmit}
                initialValues={updateAreaFrom}
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
                        label="Tên khu vực"
                        value={values.name}
                        name="name"
                        sx={{ gridColumn: 'span 3' }}
                        InputLabelProps={{
                          sx: {
                            backgroundColor: 'white',
                          },
                        }}
                        readOnly
                      />
                      <TextField
                        fullWidth
                        type="text"
                        label="Sức chứa"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.capacity}
                        name="capacity"
                        error={!!touched.capacity && !!errors.capacity}
                        helperText={touched.capacity && errors.capacity}
                        sx={{ gridColumn: 'span 3' }}
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
  name: yup.string().required('Yêu cầu nhập tên dịch vụ'),
  capacity: yup.number().required('Yêu cầu nhập sức chứa của khu vực'),
})

export default ModalUpdateArea

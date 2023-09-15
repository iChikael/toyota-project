import React, { useReducer } from 'react'
import Select from '@mui/material/Select'
import MenuItem from '@mui/material/MenuItem'
import { updateRepairItemById } from 'app/repairItemSlice'
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

const ModalUpdateRepairItem = ({ modalIsOpen, closeModal }) => {
  const dispatch = useDispatch()

  const [listServiceAreaUpdate, setListServiceAreaUpdate] = useState([])

  const repairItem = useSelector((state) => state.repairItem.data.repairItem)

  const serviceArea = useSelector(
    (state) => state.serviceArea.data.serviceAreas
  )

  const [selectId, setSelectId] = useState(repairItem.serviceArea?.id || '')

  useEffect(() => {
    if (repairItem.serviceArea && repairItem.serviceArea.id) {
      setSelectId(repairItem.serviceArea.id)
    }
  }, [repairItem.serviceArea])

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

  const [updateRepairItemFrom, setUpdateRepairItemForm] = useState({
    id: repairItem.id,
    title: repairItem.title,
    serviceArea: repairItem.serviceArea,
  })

  useEffect(() => {
    setUpdateRepairItemForm({
      id: repairItem.id,
      title: repairItem.title,
      serviceArea: repairItem.serviceArea,
    })
  }, [repairItem])

  useEffect(() => {
    setListServiceAreaUpdate(serviceArea)
    setServiceAreaId(repairItem.serviceArea?.id || '')
  }, [repairItem])

  const isNonMobile = useMediaQuery('(min-width:600px)')

  const [serviceAreaId, setServiceAreaId] = useState(
    repairItem.serviceArea?.id || ''
  )

  const showToastMessage = () => {
    toast.success('Cập nhật thông tin dịch vụ thành công!', {
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
    const serviceAreaIdSelect = serviceAreaId
    const createForm = {
      id: selectId,
      title: values.title,
      serviceArea: serviceAreaIdSelect,
    }
    const formData = new FormData()
    for (const key in createForm) {
      formData.append(key, createForm[key])
    }
    setUpdateRepairItemForm(createForm)
    const updateRepairItemAction = updateRepairItemById(createForm)
    dispatch(updateRepairItemAction)
      .unwrap()
      .then((data) => {
        showToastMessage()
        closeModal()
      })
      .catch((error) => {
        showToastFailMessage(error.message)
      })
  }

  const handleChangeServiceArea = (event) => {
    setServiceAreaId(event.target.value)
  }

  return (
    <div>
      <ToastContainer autoClose={true} />
      {modalIsOpen && (
        <Modal
          open={modalIsOpen}
          closemodal={closeModal}
          aria-label="Cập nhật thông tin dịch vụ"
        >
          <Fade in={modalIsOpen}>
            <Box sx={customStyles}>
              <h1 className="text-center brand-color">
                Cập nhật thông tin dịch vụ
              </h1>
              <Formik
                onSubmit={handleFormSubmit}
                initialValues={updateRepairItemFrom}
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
                        label="Tên dịch vụ"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.title}
                        name="title"
                        error={!!touched.title && !!errors.title}
                        helperText={touched.title && errors.title}
                        sx={{ gridColumn: 'span 3' }}
                        InputLabelProps={{
                          sx: {
                            backgroundColor: 'white',
                          },
                        }}
                      />
                      <Select
                        labelId="select-accessory-role-label"
                        fullWidth
                        type="text"
                        label="Khu vực sửa chữa"
                        value={serviceAreaId}
                        onChange={handleChangeServiceArea}
                        input={<BootstrapInput />}
                        sx={{ gridColumn: 'span 3' }}
                      >
                        {serviceArea.map((area) => {
                          return (
                            <MenuItem key={area.id} value={area.id}>
                              {area.name}
                            </MenuItem>
                          )
                        })}
                      </Select>
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
  title: yup.string().required('Yêu cầu nhập tên dịch vụ'),
})

export default ModalUpdateRepairItem

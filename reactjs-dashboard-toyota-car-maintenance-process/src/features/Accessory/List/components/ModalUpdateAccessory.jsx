import React from 'react'
// import Modal from 'react-modal'
import Select from '@mui/material/Select'
import MenuItem from '@mui/material/MenuItem'
import { EnumUnit } from 'constants/EnumUnit'
import { updateAccessoryById } from 'app/accessoriesSlice'
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
import { ToastContainer } from 'react-toastify'
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

const ModalUpdateAccessory = ({ modalIsOpen, closeModal }) => {
  console.log('ModalUpdateAccessory')

  const dispatch = useDispatch()

  const [listAccessoryRoleUpdate, setListAccessoryRoleUpdate] = useState([])

  const accessory = useSelector((state) => state.accessory.data.accessory)

  const [Enum, setEnum] = useState([])
  const [EnumStatus, setEnumStatus] = useState('')

  const accessoryRole = useSelector(
    (state) => state.accessory.data.accessoryRole
  )

  const [selectId, setSelectId] = useState(accessory.accessoryRole)

  useEffect(() => {
    const selectedItem = listAccessoryRoleUpdate.find(
      (role) => role.code === accessory.accessoryRole
    )

    if (selectedItem) {
      setAccessoryRoleId(selectedItem.id)
    }
  }, [accessory.accessoryRole, listAccessoryRoleUpdate])

  const [updateAccessoryFrom, setUpdateAccessoryForm] = useState({
    id: accessory.id,
    name: accessory.name,
    code: accessory.code,
    quantity: accessory.quantity,
    price: accessory.price,
    unit: accessory.unit,
    accessoryRole: accessory.accessoryRole,
  })

  useEffect(() => {
    setUpdateAccessoryForm({
      id: accessory.id,
      name: accessory.name,
      code: accessory.code,
      quantity: accessory.quantity,
      price: accessory.price,
      unit: accessory.unit,
      accessoryRole: accessory.accessoryRole,
    })
  }, [accessory])

  const getEnumUnit = () => {
    const enumValuesArray = Object.values(EnumUnit)
    setEnum(enumValuesArray)
    setEnumStatus(accessory.unit)
  }

  useEffect(() => {
    getEnumUnit()
    setListAccessoryRoleUpdate(accessoryRole)
    setSelectId(accessory.id)
  }, [accessory])

  const isNonMobile = useMediaQuery('(min-width:600px)')

  const [accessoryRoleId, setAccessoryRoleId] = useState()

  const handleFormSubmit = (values) => {
    const enumStatusSelect = EnumStatus
    const accessoryRoleIdSelect = accessoryRoleId
    const createForm = {
      id: selectId,
      name: values.name,
      code: values.code,
      quantity: values.quantity,
      price: values.price,
      unit: enumStatusSelect,
      accessoryRole: accessoryRoleIdSelect,
    }
    const formData = new FormData()
    for (const key in createForm) {
      formData.append(key, createForm[key])
    }

    setUpdateAccessoryForm(createForm)
    const updateAccessoryAction = updateAccessoryById(createForm)
    dispatch(updateAccessoryAction)
      .unwrap()
      .then((data) => {
        ToastUtils.showToastSuccessMessage('Cập nhật linh kiện thành công!')
        closeModal()
      })
      .catch((error) => {
        ToastUtils.showToastFailMessage(error.message)
      })
  }

  const handleChangeAccessoryRole = (event) => {
    setAccessoryRoleId(event.target.value)
  }

  const handleChangeEnumStatus = (event) => {
    setEnumStatus(event.target.value)
  }

  return (
    <div>
      <ToastContainer autoClose={true} />
      {modalIsOpen && (
        <Modal
          open={modalIsOpen}
          closemodal={closeModal}
          aria-label="Cập nhật thông tin linh kiện"
        >
          <Fade in={modalIsOpen}>
            <Box sx={customStyles}>
              <h1 className="text-center brand-color">
                Cập nhật thông tin linh kiện
              </h1>
              <Formik
                onSubmit={handleFormSubmit}
                initialValues={updateAccessoryFrom}
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
                        label="Tên linh kiện - vật tư"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.name}
                        name="name"
                        error={!!touched.name && !!errors.name}
                        helperText={touched.name && errors.name}
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
                        label="Mã CODE"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.code}
                        name="code"
                        error={!!touched.code && !!errors.code}
                        helperText={touched.code && errors.code}
                        sx={{ gridColumn: 'span 1' }}
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
                        label="Số lượng nhập"
                        variant="outlined"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.quantity}
                        name="quantity"
                        error={!!touched.quantity && !!errors.quantity}
                        helperText={touched.quantity && errors.quantity}
                        sx={{ gridColumn: 'span 2' }}
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
                        label="Loại linh kiện - vật tư"
                        value={accessoryRoleId || ''}
                        onChange={handleChangeAccessoryRole}
                        input={<BootstrapInput />}
                        sx={{ gridColumn: 'span 3' }}
                      >
                        {listAccessoryRoleUpdate.map((accessoryRole) => {
                          return (
                            <MenuItem
                              key={accessoryRole.id}
                              value={accessoryRole.id}
                            >
                              {accessoryRole.code}
                            </MenuItem>
                          )
                        })}
                      </Select>
                      <Select
                        fullWidth
                        type="text"
                        label="Đơn vị tính"
                        value={EnumStatus || ''}
                        onChange={handleChangeEnumStatus}
                        input={<BootstrapInput />}
                        sx={{ gridColumn: 'span 1' }}
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
  name: yup.string().required('Yêu cầu nhập tên linh kiện'),
  code: yup.string().required('Yêu cầu nhập code của linh kiện'),
  quantity: yup.string().required('Vui lòng nhập số lượng'),
  price: yup.string().required('Vui lòng nhập giá'),
})

export default ModalUpdateAccessory

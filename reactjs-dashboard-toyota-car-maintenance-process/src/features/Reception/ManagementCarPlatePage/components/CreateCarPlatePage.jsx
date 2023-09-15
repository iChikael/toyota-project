import React, { useState } from 'react'
import { ToastContainer } from 'react-toastify'
import { useNavigate } from 'react-router-dom'

import {
  Box,
  Button,
  Grid,
  InputBase,
  List,
  ListItemButton,
  TextField,
} from '@mui/material'

import Header from 'components/Header'
import { useDispatch } from 'react-redux'

import {
  searchCustomerByPhone,
  searchCarByName,
  createCarPlate,
} from 'app/receptionSlice'

import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import BackButton from 'components/Button/BackButton'
import BackListButton from 'components/Button/BackListButton'
import ToastUtils from 'utils/ToastUtils'

const CreateCarPlatePage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const [phone, setPhone] = useState('')

  const [customerSearchs, setCustomerSearchs] = useState(null)

  const [customerSearch, setCustomerSearch] = useState(null)

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const handleSearchCus = () => {
    const inputSearchCustomer = document.getElementById(
      'inputSearchCustomer'
    ).value

    const keyAction = searchCustomerByPhone({ phone: inputSearchCustomer })
    dispatch(keyAction)
      .unwrap()
      .then((data) => {
        setCustomerSearchs(data)
      })
      .catch((error) => {
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu khách hàng theo số điện thoại, vui lòng kiểm tra mạng'
          )
        }
      })
  }

  const handleSearchCustomer = (e) => {
    setPhone(e.target.value)
    debounce(handleSearchCus, 500)
  }

  const [name, setName] = useState('')

  const [carSearchs, setCarSearchs] = useState(null)

  const [carSearch, setCarSearch] = useState(null)

  const handleSearchCarName = () => {
    const inputSearchCar = document.getElementById('inputSearchCar').value

    const keyAction = searchCarByName({
      name: inputSearchCar,
    })
    dispatch(keyAction)
      .unwrap()
      .then((data) => {
        setCarSearchs(data)
        console.log(data)
      })
      .catch((error) => {
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu xe theo tên xe, vui lòng kiểm tra mạng'
          )
        }
      })
  }

  const handleSearchCar = (e) => {
    setName(e.target.value)
    debounce(handleSearchCarName, 500)
  }
  const handleListItemCustomerClick = (event) => {
    console.log(event.target.getAttribute('data-phone'))
    setPhone('')
    setCustomerSearchs(null)
    setCustomerSearch(event.target.getAttribute('data-phone'))
    setCarPlates({
      ...carPlates,
      username: event.target.getAttribute('data-phone'),
    })
  }

  const handleListItemCarClick = (event) => {
    console.log(event.target.getAttribute('data-name'))
    setName('')
    setCarSearchs(null)
    setCarSearch(event.target.getAttribute('data-name'))
    setCarPlates({
      ...carPlates,
      carId: event.target.getAttribute('data-id'),
    })
  }

  const [carPlate, setCarPlate] = useState('')

  const handleChangeCarPlate = (e) => {
    setCarPlate(e.target.value)
    setCarPlates({
      ...carPlates,
      carNumberPlate: e.target.value,
    })
  }

  const [carPlates, setCarPlates] = useState()

  const handleSubmitCarPlate = () => {
    const carPlateAction = createCarPlate(carPlates)
    dispatch(carPlateAction)
      .unwrap()
      .then((data) => {
        ToastUtils.showToastSuccessMessage('Thêm mới biển số xe thành công!')
      })
      .catch((error) => {
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu xe theo tên xe, vui lòng kiểm tra mạng'
          )
        } else {
          ToastUtils.showToastFailMessage(
            'Vui lòng nhập đúng định dạng biển số'
          )
        }
      })
  }

  const handleBack = () => {
    navigate(-1)
  }

  return (
    <Box m="20px" pb="20px">
      <ToastContainer autoClose={true} />
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Tạo mới biển số xe" />
        <BackListButton text="Danh sách biển số xe" onClick={handleBack} />
      </Grid>

      <InputBase
        sx={{
          ml: 2,
          flex: 1,
          width: '40%',
          border: '1px solid black',
          borderRadius: '4px',
          padding: '10px',
        }}
        label="Tìm kiếm khách hàng theo SĐT"
        placeholder="Tìm kiếm khách hàng theo SĐT"
        value={phone}
        id="inputSearchCustomer"
        onChange={(e) => {
          handleSearchCustomer(e)
        }}
      />
      <List component="nav" aria-label="main mailbox folders">
        {customerSearchs &&
          customerSearchs.map((item, index) => (
            <ListItemButton
              key={index}
              onClick={(event) => handleListItemCustomerClick(event)}
              data-phone={item.phone}
            >
              {item.phone}
            </ListItemButton>
          ))}
      </List>

      {customerSearch && (
        <Box
          sx={{
            fontSize: '1.5rem',
            ml: '17px',
            mb: '10px',
          }}
        >
          {customerSearch}
        </Box>
      )}
      <InputBase
        sx={{
          ml: 2,
          flex: 1,
          width: '40%',
          border: '1px solid black',
          borderRadius: '4px',
          padding: '10px',
        }}
        label="TÌm kiếm xe theo tên"
        placeholder="Tìm kiếm xe theo tên"
        value={name}
        id="inputSearchCar"
        onChange={(e) => {
          handleSearchCar(e)
        }}
      />

      <List
        component="nav"
        aria-label="main mailbox folders"
        sx={{ width: '100%' }}
      >
        {carSearchs &&
          carSearchs.map((item, index) => (
            <ListItemButton
              sx={{
                fontSize: '0,75rem',
                padding: '5px',
              }}
              key={index}
              onClick={(event) => handleListItemCarClick(event)}
              data-id={item.id}
              data-name={item.title}
            >
              {item.title}
            </ListItemButton>
          ))}
      </List>

      {carSearch && (
        <Box
          sx={{
            fontSize: '1.5rem',
            ml: '17px',
            mb: '10px',
          }}
        >
          {carSearch}
        </Box>
      )}
      <TextField
        fullWidth
        variant="filled"
        type="text"
        label="Biển số xe"
        onChange={(e) => {
          handleChangeCarPlate(e)
        }}
        value={carPlate}
        name="carPlate"
        sx={{ gridColumn: 'span 2', width: '41%', padding: '10px', ml: '8px' }}
      />
      <Box display="flex" justifyContent="start" mt="20px" ml="18px">
        {/* <BackButton onClick={handleBack} /> */}
        <Button
          type="button"
          color="secondary"
          variant="contained"
          onClick={handleSubmitCarPlate}
          sx={{ ml: '10px', mt: 1 }}
        >
          Tạo mới biển số
        </Button>
      </Box>
    </Box>
  )
}

export default CreateCarPlatePage

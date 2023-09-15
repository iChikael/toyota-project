import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'

import { Box, Button, IconButton, InputBase, useTheme } from '@mui/material'
import SearchIcon from '@mui/icons-material/Search'
import { tokens } from 'theme'

import ListManagementCarPlate from './ListManagementCarPlate'
import { searchCarPlate } from 'app/receptionSlice'
import Header from 'components/Header'
import ToastUtils from 'utils/ToastUtils'
import { ToastContainer } from 'react-toastify'

const ManagementCarPlatePage = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)

  const [key, setKey] = useState('')

  const [carPlateSearch, setCarPlateSearch] = useState(null)

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const handleSearch = () => {
    const inputSearchCarPlate = document.getElementById(
      'inputSearchCarPlate'
    ).value

    const keyAction = searchCarPlate({ key: inputSearchCarPlate })
    dispatch(keyAction)
      .unwrap()
      .then((data) => {
        setCarPlateSearch(data)
      })
      .catch((error) => {
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách quản lý biển số xe, vui lòng kiểm tra mạng'
          )
        }
      })
  }

  const handleSearchCarPlate = (e) => {
    setKey(e.target.value)
    debounce(handleSearch, 500)
  }

  useEffect(() => {}, [carPlateSearch])

  const handleRegisterCustomer = () => {
    navigate('/customers/create')
  }

  const handleCreateCarQueue = () => {
    navigate('/car-queues/create')
  }
  const handleCreateCarPlate = () => {
    navigate('/car-plate/create')
  }
  const handleCreateOrder = () => {
    navigate('/reception/order-service/create')
  }

  return (
    <>
      <Header title="Danh sách khách hàng đã có biển số xe" />
      <ToastContainer />
      <Box
        display="flex"
        backgroundColor={colors.primary[400]}
        borderRadius="3px"
        sx={{ width: '100%' }}
      >
        <InputBase
          sx={{ ml: 2, flex: 1, width: 300 }}
          placeholder="Search"
          value={key}
          id="inputSearchCarPlate"
          onChange={(e) => {
            handleSearchCarPlate(e)
          }}
        />
        <IconButton type="button" sx={{ p: 1 }}>
          <SearchIcon />
        </IconButton>
        <Button
          variant="contained"
          type="button"
          onClick={handleRegisterCustomer}
          color="secondary"
        >
          Create Customer
        </Button>
        <Button
          variant="contained"
          type="button"
          onClick={handleCreateCarQueue}
          color="secondary"
        >
          Create Car Queue
        </Button>
        <Button
          variant="contained"
          type="button"
          onClick={handleCreateCarPlate}
          color="secondary"
        >
          Create Car Plate
        </Button>
        <Button
          variant="contained"
          type="button"
          onClick={handleCreateOrder}
          color="secondary"
        >
          Create Order
        </Button>
      </Box>
      <ListManagementCarPlate data={carPlateSearch} />
    </>
  )
}

export default ManagementCarPlatePage

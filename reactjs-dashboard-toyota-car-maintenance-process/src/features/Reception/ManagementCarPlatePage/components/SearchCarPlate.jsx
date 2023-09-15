import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'

import { Grid, OutlinedInput } from '@mui/material'

import { searchCarPlate, setCarPlateSearchs } from 'app/receptionSlice'
import ToastUtils from 'utils/ToastUtils'
import { ToastContainer } from 'react-toastify'
import AddButton from 'components/Button/AddButton'

const SearchCarPlate = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()

  const [key, setKey] = useState('')

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

    if (inputSearchCarPlate !== '') {
      dispatch(searchCarPlate({ key: inputSearchCarPlate }))
        .unwrap()
        .catch((err) => {
          if (err.code === 'ERR_NETWORK') {
            ToastUtils.showToastFailMessage(
              'Lỗi khi tải dữ liệu biển số xe, vui lòng kiểm tra mạng'
            )
          }
        })
    } else {
      dispatch(setCarPlateSearchs(null))
    }
  }

  const handleSearchCarPlate = (e) => {
    setKey(e.target.value)
    debounce(handleSearch, 500)
  }

  const handleRegisterCustomer = () => {
    navigate('/customers/create')
  }

  const handleCreateCarPlate = () => {
    navigate('/reception/car-plates/create')
  }

  return (
    <Grid container item md={12}>
      <ToastContainer />
      <Grid item md={6}>
        <OutlinedInput
          sx={{
            flex: 1,
            width: '50%',
            variant: 'outline',
          }}
          placeholder="Tìm kiếm biển số xe"
          value={key}
          id="inputSearchCarPlate"
          onChange={(e) => {
            handleSearchCarPlate(e)
          }}
        />
      </Grid>
      <Grid
        item
        md={6}
        sx={{ display: 'flex', justifyContent: 'right', gap: '10px' }}
      >
        <AddButton text="Thêm mới biển số xe" onClick={handleCreateCarPlate} />
        <AddButton
          text="Thêm mới khách hàng"
          onClick={handleRegisterCustomer}
        />
      </Grid>
    </Grid>
  )
}

export default SearchCarPlate

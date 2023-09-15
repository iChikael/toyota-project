import React, { useEffect, useState } from 'react'

import { Box, Button, Grid } from '@mui/material'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'

import Header from 'components/Header'
import { useTheme } from '@mui/material'
import { useDispatch, useSelector } from 'react-redux'

import SearchCarPlate from './SearchCarPlate'
import { getAllCarPlates, getCarPlateById } from 'app/receptionSlice'
import Helper from 'utils/Helper'
import { useNavigate } from 'react-router-dom'
import Loading from 'components/Loading'
import ToastUtils from 'utils/ToastUtils'
import AddButton from 'components/Button/AddButton'

const ListManagementCarPlate = () => {
  const dispatch = useDispatch()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const navigate = useNavigate()

  const carPlates = useSelector((state) => state.reception.data.carPlates)

  const carPlateSearchs = useSelector(
    (state) => state.reception.data.carPlateSearchs
  )

  const [loading, setLoading] = useState(true)

  const handleGetAllCarPlates = () => {
    dispatch(getAllCarPlates())
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách quản lý biển số xe, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  const handleCreateCarQueueByCarPlate = (id) => {
    dispatch(getCarPlateById({ id: id }))
    navigate('/reception/car-queues/create')
  }

  useEffect(() => {
    handleGetAllCarPlates()
  }, [])

  const columns = [
    { field: 'id', headerName: 'ID', headerAlign: 'center', align: 'center' },
    {
      field: 'carName',
      headerName: 'Tên xe',
      headerAlign: 'center',
      align: 'left',
      width: 200,
    },
    {
      field: 'carPlate',
      headerName: 'Biển số xe',
      valueGetter: ({ row }) => Helper.formatCarNumberPlate(row.carPlate),
      headerAlign: 'center',
      align: 'center',
      width: 180,
    },
    {
      field: 'customerName',
      headerName: 'Tên khách hàng',
      headerAlign: 'center',
      align: 'left',
      width: 220,
    },
    {
      field: 'phone',
      headerName: 'Số điện thoại',
      valueGetter: ({ row }) => Helper.formatPhoneNumber(row.phone),
      headerAlign: 'center',
      align: 'center',
      width: 150,
    },
    {
      field: 'email',
      headerName: 'Email',
      headerAlign: 'center',
      align: 'left',
      width: 200,
    },
    {
      field: 'actions',
      headerName: 'Chức năng',
      headerAlign: 'center',
      width: 120,
      sortable: false,
      disableColumnMenu: true,
      renderCell: (params) => {
        return (
          <Box
            sx={{
              width: '100%',
              height: '100%',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              size: 'medium',
            }}
          >
            <Button
              variant="contained"
              type="button"
              onClick={() => {
                handleCreateCarQueueByCarPlate(params.id)
              }}
              color="secondary"
              sx={{ ml: '10px' }}
            >
              Tạo xe chờ
            </Button>
          </Box>
        )
      },
    },
  ]

  return (
    <Box m="20px" pb="20px">
      {loading && <Loading />}
      <Header title="Quản lý biển số" />
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <SearchCarPlate />
      </Grid>

      <Box
        m="20px 0 0 0"
        height="75vh"
        sx={{
          '& .MuiDataGrid-root': {
            border: 'none',
            fontSize: '1rem',
          },
          '& .MuiDataGrid-cell': {
            borderBottom: 'none',
          },
          '& .name-column--cell': {
            color: colors.greenAccent[300],
          },
          '& .MuiDataGrid-columnHeaders': {
            backgroundColor: colors.blueAccent[700],
            borderBottom: 'none',
          },
          '& .MuiDataGrid-virtualScroller': {
            backgroundColor: colors.primary[400],
          },
          '& .MuiDataGrid-footerContainer': {
            borderTop: 'none',
            backgroundColor: colors.blueAccent[700],
          },
          '& .MuiCheckbox-root': {
            color: `${colors.greenAccent[200]} !important`,
          },
          '& .MuiDataGrid-toolbarContainer .MuiButton-text': {
            color: `${colors.grey[100]} !important`,
          },
        }}
      >
        <DataGrid
          rows={
            carPlateSearchs !== null
              ? carPlateSearchs !== ''
                ? carPlateSearchs
                : []
              : carPlates
          }
          columns={columns}
          components={{ Toolbar: GridToolbar }}
          initialState={{
            sorting: {
              sortModel: [{ field: 'id', sort: 'asc' }],
            },
          }}
        />
      </Box>
    </Box>
  )
}

export default ListManagementCarPlate

import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

import { Box, Button, Grid } from '@mui/material'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'

import Header from 'components/Header'
import { useTheme } from '@mui/material'
import { getAllOrderServiceByCurrentServiceArea } from 'app/technicalSlice'
import Helper from 'utils/Helper'
import Loading from 'components/Loading'
import ToastUtils from 'utils/ToastUtils'
import { ToastContainer } from 'react-toastify'
import BackListButton from 'components/Button/BackListButton'

const ListOrderServiceUseWashArea = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const serviceAreaId = 5

  const viewOrderServicesByServiceArea = useSelector(
    (state) => state.technical.data.viewOrderServicesByServiceArea
  )

  const [loading, setLoading] = useState(true)

  const handleGetAllOrderServicesByServiceArea = () => {
    const action = getAllOrderServiceByCurrentServiceArea({
      serviceAreaId: serviceAreaId,
    })

    dispatch(action)
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách đơn hàng, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  const columns = [
    { field: 'id', headerName: 'ID', headerAlign: 'center', align: 'center' },
    {
      headerName: 'Biển số xe',
      valueGetter: ({ row }) => Helper.formatCarNumberPlate(row.carPlate),
      headerAlign: 'center',
      align: 'center',
      width: 140,
    },
    {
      field: 'customerReq',
      headerName: 'Yêu cầu khách hàng',
      headerAlign: 'center',
      align: 'left',
      width: 220,
    },
    {
      field: 'doEarly',
      headerName: 'Mục làm sớm',
      headerAlign: 'center',
      align: 'left',
      width: 220,
    },
    {
      field: 'status',
      headerName: 'Trạng thái',
      headerAlign: 'center',
      align: 'center',
      cellClassName: 'name-column--cell',
      width: 140,
    },
    {
      field: 'currentServiceArea',
      headerName: 'Khu vực hiện tại',
      headerAlign: 'center',
      align: 'center',
      cellClassName: 'name-column--cell',
      width: 200,
    },
    {
      field: 'actions',
      headerName: 'Chi tiết',
      headerAlign: 'center',
      align: 'center',
      width: 220,
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
              color="secondary"
              onClick={() =>
                handleClickDetailOrderServiceUseGeneralRepairArea(params.id)
              }
            >
              Xem chi tiết công việc
            </Button>
          </Box>
        )
      },
    },
  ]

  const handleClickDetailOrderServiceUseGeneralRepairArea = (orderId) => {
    navigate('/technical/service-area/wash-area/order-services/' + orderId)
  }
  const handleBackListOrderService = () => {
    navigate('/technical/order-services')
  }

  useEffect(() => {
    handleGetAllOrderServicesByServiceArea()
  }, [])

  return (
    <Box m="20px">
      {loading && <Loading />}
      <ToastContainer />
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Khu vực rửa xe" />
        <BackListButton
          text="Danh sách lệnh sửa chữa"
          onClick={handleBackListOrderService}
        />
      </Grid>
      <Box
        m="20px 0 0 0"
        height="75vh"
        sx={{
          '& .MuiDataGrid-root': {
            border: 'none',
            fontSize: '1rem',
            textAglins: 'center',
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
          rows={viewOrderServicesByServiceArea}
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

export default ListOrderServiceUseWashArea

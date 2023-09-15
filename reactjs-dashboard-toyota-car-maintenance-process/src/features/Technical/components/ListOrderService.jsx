import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

import { Box, Button } from '@mui/material'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'

import Header from 'components/Header'
import { useTheme } from '@mui/material'
import { getAllOrderServices, setOrderServices } from 'app/technicalSlice'
import { EnumStatusOrderService } from 'constants/EStatusOrderService'
import Helper from 'utils/Helper'
import Loading from 'components/Loading'
import { ToastContainer } from 'react-toastify'
import ToastUtils from 'utils/ToastUtils'

import socketApi from 'api/socketApi'

const socket = socketApi.connectServer()

let ts

const ListOrderService = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)

  socket.on('get-all-order-service-success', (res) => {
    if (ts !== res.ts) {
      ts = res.ts

      const action = setOrderServices(res.data)
      dispatch(action)
    }
  })

  const viewOrderServices = useSelector(
    (state) => state.technical.data.viewOrderServices
  )
  const [loading, setLoading] = useState(true)

  const handleGetAllOrderServices = () => {
    dispatch(getAllOrderServices())
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
      valueGetter: ({ row }) =>
        row.currentServiceArea ? row.currentServiceArea : 'Khu vực xe chờ',
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
              onClick={() => handleClickDetailOrderService(params.id)}
              disabled={
                params.row.status ===
                EnumStatusOrderService.STATUS_DONE.statusValue
              }
            >
              Điều xe
            </Button>
          </Box>
        )
      },
    },
  ]

  const handleClickDetailOrderService = (orderId) => {
    navigate('/technical/order-services/' + orderId)
  }

  useEffect(() => {
    handleGetAllOrderServices()
  }, [])

  return (
    <Box m="20px">
      {loading && <Loading />}
      <ToastContainer />
      <Header title="Danh sách xe bảo dưỡng, sửa chữa" />
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
          rows={
            viewOrderServices
            // .filter(
            //   (service) => service.status !== 'Hoàn thành'
            // )
          }
          columns={columns}
          components={{ Toolbar: GridToolbar }}
        />
      </Box>
    </Box>
  )
}

export default ListOrderService

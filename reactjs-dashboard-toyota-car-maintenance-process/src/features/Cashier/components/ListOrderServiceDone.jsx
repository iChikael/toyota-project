import { Box, Button } from '@mui/material'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'

import Header from 'components/Header'
import { useTheme } from '@mui/material'
import { useDispatch, useSelector } from 'react-redux'
import { useEffect, useState } from 'react'
import { getAllOrderServicesDone, setAllOrderServicesDone } from 'app/cashierSlice'
import { useNavigate } from 'react-router-dom'
import Helper from 'utils/Helper'
import Loading from 'components/Loading'
import ToastUtils from 'utils/ToastUtils'
import { ToastContainer } from 'react-toastify'

import socketApi from 'api/socketApi'

const socket = socketApi.connectServer()

let ts

const ListOrderServiceDone = () => {
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const dispatch = useDispatch()
  const navigate = useNavigate()

  socket.on('get-all-order-service-done-success', (res) => {
    if (ts !== res.ts) {
      ts = res.ts

      const action = setAllOrderServicesDone(res.data)
      dispatch(action)
    }
  })

  const viewOrderServicesDone = useSelector(
    (state) => state.cashier.data.viewOrderServicesDone
  )

  const [loading, setLoading] = useState(true)

  const handleGetAllOrderServicesDone = () => {
    dispatch(getAllOrderServicesDone())
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách hóa đơn chờ thanh toán, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  const handleClickPaymentOrderService = (orderServiceId) => {
    navigate('/cashier/order-services-done/' + orderServiceId)
  }

  const columns = [
    { field: 'id', headerName: 'ID', headerAlign: 'center', align: 'center' },
    {
      field: 'customerName',
      headerName: 'Họ tên khách hàng',
      headerAlign: 'center',
      align: 'left',
      width: 250,
    },
    {
      headerName: 'Biển số xe',
      valueGetter: ({ row }) => Helper.formatCarNumberPlate(row.carPlate),
      headerAlign: 'center',
      align: 'center',
      width: 200,
    },
    {
      field: 'carVehicle',
      headerName: 'Dòng xe',
      headerAlign: 'center',
      align: 'center',
      width: 250,
    },

    {
      field: 'statusPayment',
      headerName: 'Trạng thái',
      headerAlign: 'center',
      align: 'center',
      cellClassName: 'name-column--cell',
      width: 150,
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
              onClick={() => handleClickPaymentOrderService(params.id)}
            >
              Thanh toán
            </Button>
          </Box>
        )
      },
    },
  ]

  useEffect(() => {
    handleGetAllOrderServicesDone()
  }, [])
  
  return (
    <Box m="20px">
      {loading && <Loading />}
      <ToastContainer />
      <Header title="Danh sách xe chờ thanh toán" />
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
          rows={viewOrderServicesDone}
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

export default ListOrderServiceDone

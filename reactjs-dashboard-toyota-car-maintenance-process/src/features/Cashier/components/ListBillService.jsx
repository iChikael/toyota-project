import { Box, Button } from '@mui/material'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'

import Header from 'components/Header'
import { useTheme } from '@mui/material'
import { useDispatch, useSelector } from 'react-redux'
import { getAllBillServices } from 'app/cashierSlice'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import moment from 'moment'
import Loading from 'components/Loading'
import { ToastContainer } from 'react-toastify'
import ToastUtils from 'utils/ToastUtils'

const ListBillService = () => {
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const viewBillServices = useSelector(
    (state) => state.cashier.data.viewBillServices
  )

  const [loading, setLoading] = useState(true)

  const handleGetAllBillServices = () => {
    dispatch(getAllBillServices())
      .unwrap()
      .catch((err) => {
        console.log(err)
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách hóa đơn, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  const handleClickBillServiceDetail = (id) => {
    navigate('/cashier/bill-services/' + id)
  }

  const columns = [
    { field: 'id', headerName: 'ID', headerAlign: 'center', align: 'center' },
    {
      field: 'customerName',
      headerName: 'Họ tên khách hàng',
      headerAlign: 'center',
      align: 'left',
      width: 200,
    },
    {
      field: 'carPlate',
      headerName: 'Biển số xe',
      headerAlign: 'center',
      align: 'center',

      width: 150,
    },
    {
      field: 'carVehicle',
      headerName: 'Dòng xe',
      headerAlign: 'center',
      align: 'center',
      width: 150,
    },
    {
      field: 'createdAt',
      headerName: 'Ngày lập HĐ',
      valueGetter: ({ row }) => moment(row.createdAt).format('L'),
      headerAlign: 'center',
      align: 'center',
      width: 150,
    },
    {
      field: 'totalAmount',
      headerName: 'Tổng tiền sửa chữa',
      headerAlign: 'center',
      align: 'right',
      width: 150,
    },
    {
      field: 'totalAmountAfterTax',
      headerName: 'Tổng tiền sau thuế',
      headerAlign: 'center',
      align: 'right',
      width: 150,
    },
    {
      field: 'actions',
      headerName: 'Chi tiết',
      headerAlign: 'center',
      align: 'center',
      width: 150,
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
              onClick={() => handleClickBillServiceDetail(params.id)}
            >
              Chi tiết
            </Button>
          </Box>
        )
      },
    },
  ]

  useEffect(() => {
    handleGetAllBillServices()
  }, [])

  return (
    <Box m="20px" pb="20px">
      {loading && <Loading />}
      <ToastContainer />
      <Header title="Danh sách hóa đơn" />
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
          rows={viewBillServices}
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

export default ListBillService

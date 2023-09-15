import { Box, Typography, useTheme } from '@mui/material'
import Header from 'components/Header'
import React, { useEffect, useState } from 'react'
import RevenueByMonth from './components/RevenueByMonth'
import Loading from 'components/Loading'
import { getRevenueByMonth } from 'app/cashierSlice'
import { useDispatch, useSelector } from 'react-redux'
import { tokens } from 'theme'
import Helper from 'utils/Helper'
import moment from 'moment'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import dayjs from 'dayjs'
import ToastUtils from 'utils/ToastUtils'

const RevenueByMonthPage = ({ month }) => {
  const theme = useTheme()
  const dispatch = useDispatch()
  const colors = tokens(theme.palette.mode)

  const revenueByMonth = useSelector(
    (state) => state.cashier.data.revenueByMonth
  )
  const viewBillServicesByMonth = useSelector(
    (state) => state.cashier.data.viewBillServicesByMonth
  )

  const [loaded, setLoaded] = useState(true)

  const [loading, setLoading] = useState({
    getRevenueByMonth: true,
  })

  const checkLoading = () => {
    let pageLoading = false
    Object.entries(loading).forEach((k, v) => {
      if (v === true) {
        pageLoading = true
      }
    })

    if (!pageLoading) {
      setLoaded(false)
    }
  }

  const handleGetRevenueByMonth = () => {
    dispatch(getRevenueByMonth({ month: month }))
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu doanh thu, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading({ ...loading, getRevenueByMonth: false })
      })
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
    // {
    //   field: 'actions',
    //   headerName: 'Chi tiết',
    //   headerAlign: 'center',
    //   align: 'center',
    //   width: 150,
    //   sortable: false,
    //   disableColumnMenu: true,

    //   renderCell: (params) => {
    //     return (
    //       <Box
    //         sx={{
    //           width: '100%',
    //           height: '100%',
    //           display: 'flex',
    //           justifyContent: 'center',
    //           alignItems: 'center',
    //           size: 'medium',
    //         }}
    //       >
    //         <Button
    //           variant="contained"
    //           type="button"
    //           color="secondary"
    //           onClick={() => handleClickBillServiceDetail(params.id)}
    //         >
    //           Chi tiết
    //         </Button>
    //       </Box>
    //     )
    //   },
    // },
  ]

  useEffect(() => {
    checkLoading()
  }, [JSON.stringify(loading)])

  useEffect(() => {
    handleGetRevenueByMonth()
  }, [month])
  return (
    <>
      <Box m="20px">
        {loaded && <Loading />}
        <Box
          gridColumn="span 8"
          gridRow="span 2"
          backgroundColor={colors.primary[400]}
        >
          <Box
            mt="25px"
            p="0 30px"
            display="flex "
            justifyContent="space-between"
            alignItems="center"
          >
            <Box>
              <Typography
                variant="h5"
                fontWeight="600"
                color={colors.grey[100]}
              >
                Doanh thu sửa chữa, bảo dưỡng
              </Typography>
              <Typography
                variant="h3"
                fontWeight="bold"
                color={colors.greenAccent[500]}
              >
                {revenueByMonth &&
                  revenueByMonth.totalAmountByMonth &&
                  Helper.formatCurrencyToVND(revenueByMonth.totalAmountByMonth)}
              </Typography>
            </Box>
          </Box>
          <Box height="250px" m="-20px 0 0 0">
            {revenueByMonth && revenueByMonth.revenueByDayDTOS?.length && (
              <RevenueByMonth
                isDashboard={true}
                revenueByDayDTOS={revenueByMonth.revenueByDayDTOS}
                month={revenueByMonth.month}
              />
            )}
          </Box>
        </Box>
      </Box>
      <Box m="20px" pb="20px">
        <Header title="Thống kê đơn hàng" />
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
            rows={viewBillServicesByMonth}
            columns={columns}
            components={{ Toolbar: GridToolbar }}
          />
        </Box>
      </Box>
    </>
  )
}

export default RevenueByMonthPage

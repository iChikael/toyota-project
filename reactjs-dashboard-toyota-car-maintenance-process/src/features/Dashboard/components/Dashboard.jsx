import { Box, Button, Typography, useTheme } from '@mui/material'
import { tokens } from 'theme'

import PersonAddIcon from '@mui/icons-material/PersonAdd'

import Header from 'components/Header'

import StatBox from 'components/StatBox'

import ReceiptOutlinedIcon from '@mui/icons-material/ReceiptOutlined'
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router'

import { useEffect, useState } from 'react'
import {
  getRevenueByYear,
  getTopFiveBillServicesRecent,
} from 'app/cashierSlice'
import Loading from 'components/Loading'
import moment from 'moment'
import Helper from 'utils/Helper'
import RevenueByYearChart from './RevenueByYearChart'
import ToastUtils from 'utils/ToastUtils'
import { getInfoDashboard } from 'app/dashboardSlice'
import { ToastContainer } from 'react-toastify'

import { AUTH } from 'constants/global'

import socketApi from 'api/socketApi'

const accessToken = localStorage.getItem(AUTH.ACCESS_TOKEN)

const socket = socketApi.connectServer()

let ts

const Dashboard = () => {
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const dispatch = useDispatch()
  const navigate = useNavigate()
  let date = new Date()

  socket.emit('get-all-order-service-done', accessToken)

  const [dashboardInfo, setDashboardInfo] = useState()

  const billServiceTopFiveRecents = useSelector(
    (state) => state.cashier.data.billServiceTopFiveRecents
  )

  const revenueByYear = useSelector((state) => state.cashier.data.revenueByYear)

  const [loaded, setLoaded] = useState(true)
  const [loading, setLoading] = useState({
    getInfoDashboard: true,
    getTopFiveBillServicesRecent: true,
    getRevenueByYear: true,
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

  const handleGetAllInfo = () => {
    dispatch(getInfoDashboard())
      .unwrap()
      .then((data) => {
        setDashboardInfo(data)
      })
      .catch((err) => {
        console.log(err)
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu thông số, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading({ ...loading, getInfoDashboard: false })
      })

    dispatch(getTopFiveBillServicesRecent())
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu doanh thu năm, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading({ ...loading, getTopFiveBillServicesRecent: false })
      })

    dispatch(getRevenueByYear({ year: date.getFullYear().toString() }))
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu doanh thu gần đây, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading({ ...loading, getRevenueByYear: false })
      })
  }

  const handleNavigationCustomers = () => {
    navigate('/customers')
  }
  const handleNavigationCars = () => {
    navigate('/cars')
  }
  const handleNavigationStaffs = () => {
    navigate('/staffs')
  }
  const handleNavigationBillServices = () => {
    navigate('/cashier/bill-services')
  }

  const handleNavigationBillServiceDetail = (id) => {
    navigate('/cashier/bill-services/' + id)
  }

  useEffect(() => {
    checkLoading()
  }, [JSON.stringify(loading)])

  useEffect(() => {
    handleGetAllInfo()
  }, [])

  return (
    <Box m="20px">
      {/* HEADER */}
      <ToastContainer />
      {loaded && <Loading />}
      <Box display="flex" justifyContent="space-between" alignItems="center">
        <Header title="DASHBOARD" subtitle="Welcome to your dashboard" />
      </Box>

      {/* GRID & CHARTS */}
      <Box
        display="grid"
        gridTemplateColumns="repeat(12, 1fr)"
        gridAutoRows="140px"
        gap="20px"
      >
        {/* ROW 1 */}
        <Box
          gridColumn="span 3"
          backgroundColor={colors.primary[400]}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <Button
            onClick={handleNavigationCustomers}
            display="flex"
            alignItems="center"
            justifyContent="center"
            sx={{ width: '100%', height: '100%' }}
          >
            <StatBox
              title={dashboardInfo?.quantityCustomer || 0}
              subtitle="Khách hàng"
              progress="0.75"
              increase="+14%"
              icon={
                <PersonAddIcon
                  sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
                />
              }
            />
          </Button>
        </Box>
        <Box
          gridColumn="span 3"
          backgroundColor={colors.primary[400]}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <Button
            onClick={handleNavigationCars}
            display="flex"
            alignItems="center"
            justifyContent="center"
            sx={{ width: '100%', height: '100%' }}
          >
            <StatBox
              title={dashboardInfo?.quantityCar || 0}
              subtitle="Mẫu Xe"
              progress="0.50"
              increase="+21%"
              icon={
                <DirectionsCarIcon
                  sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
                />
              }
            />
          </Button>
        </Box>
        <Box
          gridColumn="span 3"
          backgroundColor={colors.primary[400]}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <Button
            onClick={handleNavigationStaffs}
            display="flex"
            alignItems="center"
            justifyContent="center"
            sx={{ width: '100%', height: '100%' }}
          >
            <StatBox
              title={dashboardInfo?.quantityStaff || 0}
              subtitle="Nhân viên"
              progress="0.30"
              increase="+5%"
              icon={
                <PersonAddIcon
                  sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
                />
              }
            />
          </Button>
        </Box>
        <Box
          gridColumn="span 3"
          backgroundColor={colors.primary[400]}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <Button
            onClick={handleNavigationBillServices}
            display="flex"
            alignItems="center"
            justifyContent="center"
            sx={{ width: '100%', height: '100%' }}
          >
            <StatBox
              title={dashboardInfo?.quantityBillService || 0}
              subtitle="Đơn sửa chữa"
              progress="0.80"
              increase="+43%"
              icon={
                <ReceiptOutlinedIcon
                  sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
                />
              }
            />
          </Button>
        </Box>

        {/* ROW 2 */}
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
                {revenueByYear &&
                  revenueByYear.totalAmountByYear &&
                  Helper.formatCurrencyToVND(revenueByYear.totalAmountByYear)}
              </Typography>
            </Box>
          </Box>
          <Box height="250px" m="-20px 0 0 0">
            {revenueByYear && revenueByYear.revenueByMonthDTOS?.length && (
              <RevenueByYearChart
                isDashboard={true}
                revenueByMonthDTOS={revenueByYear.revenueByMonthDTOS}
              />
            )}
          </Box>
        </Box>
        <Box
          gridColumn="span 4"
          gridRow="span 2"
          backgroundColor={colors.primary[400]}
          overflow="auto"
        >
          <Box
            display="flex"
            justifyContent="space-between"
            alignItems="center"
            borderBottom={`4px solid ${colors.primary[500]}`}
            colors={colors.grey[100]}
            p="15px"
          >
            <Typography color={colors.grey[100]} variant="h5" fontWeight="600">
              Đơn sửa chữa gần đây
            </Typography>
          </Box>
          {billServiceTopFiveRecents &&
            billServiceTopFiveRecents.map((bill, i) => (
              <Box
                key={`${bill.txId}-${i}`}
                display="flex"
                justifyContent="space-between"
                alignItems="center"
                borderBottom={`4px solid ${colors.primary[500]}`}
                p="15px"
              >
                <Box>
                  <Typography
                    color={colors.greenAccent[500]}
                    variant="h5"
                    fontWeight="600"
                  >
                    {bill.id}
                  </Typography>
                </Box>
                <Box>
                  <Typography color={colors.grey[100]}>
                    {bill.carPlate &&
                      Helper.formatCarNumberPlate(bill.carPlate)}
                  </Typography>
                </Box>
                <Box color={colors.grey[100]}>
                  {bill.createdAt && moment(bill.createdAt).format('L')}
                </Box>
                <Box
                  backgroundColor={colors.greenAccent[500]}
                  borderRadius="4px"
                >
                  <Button
                    onClick={() => {
                      handleNavigationBillServiceDetail(bill.id)
                    }}
                    sx={{ width: '80px' }}
                  >
                    {bill.totalAmountAfterTax &&
                      Helper.formatCurrencyToVND(bill.totalAmountAfterTax)}
                  </Button>
                </Box>
              </Box>
            ))}
        </Box>
      </Box>
    </Box>
  )
}

export default Dashboard

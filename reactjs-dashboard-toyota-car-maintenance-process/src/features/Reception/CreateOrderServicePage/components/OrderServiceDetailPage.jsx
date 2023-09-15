import React, { useEffect, useState, useRef } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer, toast } from 'react-toastify'

import {
  Box,
  Button,
  Grid,
  MenuItem,
  Paper,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Typography,
  useTheme,
} from '@mui/material'

import Images from 'constants/images'
import { tokens } from 'theme'
import { EnumePayment } from 'constants/EPayment'
import Helper from 'utils/Helper'
import ReactToPrint from 'react-to-print'

import {
  createOrderService,
  setMaintenanceAccessory,
  setPaymentMaintenanceAccessory,
  setPaymentRepairAccessory,
  setPaymentRepairItem,
  setRepairAccessory,
  setRepairItem,
  setUnitRepairItem,
} from 'app/receptionSlice'
import moment from 'moment'
import { LoadingButton } from '@mui/lab'
import ToastUtils from 'utils/ToastUtils'

import { AUTH } from 'constants/global'

import socketApi from 'api/socketApi'

const socket = socketApi.connectServer()

const accessToken = localStorage.getItem(AUTH.ACCESS_TOKEN)

const OrderServiceDetailPage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const goBack = () => navigate(-1)

  const orderServicePrev = useSelector(
    (state) => state.reception.data.orderServicePrev
  )

  const [payments, setPayments] = useState([])
  const [paymentMaintenance, setPaymentMaintenance] = useState('')

  const handleUpdateOrderService = () => {
    navigate('/reception/order-services/update')
  }

  const getEnumePayment = () => {
    let payments = Object.values(EnumePayment)
    setPayments(payments)
    setPaymentMaintenance(EnumePayment.PAYMENT_CUSTOMER)
  }

  const handleChangePaymentMaintenance = (e) => {
    setPaymentMaintenance(e.target.value)
    if (e.target.value === 'NB') {
      setTotalAmoutService(
        totalAmoutService -
          +orderServicePrev.maintenance.maintenanceByIds.quantityWage *
            +orderServicePrev.maintenance.maintenanceByIds.priceWage
      )
    } else {
      setTotalAmoutService(
        totalAmoutService +
          +orderServicePrev.maintenance.maintenanceByIds.quantityWage *
            +orderServicePrev.maintenance.maintenanceByIds.priceWage
      )
    }
  }

  const handleChangePaymentRepair = (e, index) => {
    const newObject = { ...orderServicePrev }
    const temp = JSON.parse(JSON.stringify(newObject.repairSelects[index]))
    temp.payment = e.target.value
    const action = setPaymentRepairItem({ index, newObject: temp })
    dispatch(action)
    setIsPaymentValid(false)
  }

  const handleChangeUnitRepair = (e, index) => {
    const newObject = { ...orderServicePrev }
    const temp = JSON.parse(JSON.stringify(newObject.repairSelects[index]))
    temp.unit = e.target.value
    const action = setUnitRepairItem({ index, newObject: temp })
    setIsUnitValid(false)
    dispatch(action)
  }

  const handleChangePaymentMaintenanceAccessory = (e, index) => {
    const newObject = { ...orderServicePrev }
    const temp = JSON.parse(
      JSON.stringify(newObject.maintenance.accessories[index].accessory)
    )
    temp.payment = e.target.value
    const action = setPaymentMaintenanceAccessory({ index, newObject: temp })
    dispatch(action)
  }

  const handleChangeQuantityWageMaintenanceAccessory = (e, index) => {
    const newObject = { ...orderServicePrev }
    const temp = JSON.parse(
      JSON.stringify(newObject.maintenance.accessories[index].accessory)
    )
    temp.quantity = +e.target.value
    temp.amount = +e.target.value * temp.price
    const action = setMaintenanceAccessory({ index, newObject: temp })
    dispatch(action)
    setIsQuantityValid(false)
  }

  const handleChangePaymentRepairAccessory = (e, index) => {
    const newObject = { ...orderServicePrev }
    const temp = JSON.parse(JSON.stringify(newObject.repairs[index].accessory))
    temp.payment = e.target.value

    const action = setPaymentRepairAccessory({ index, newObject: temp })
    dispatch(action)
  }

  const [quantityChangeCount, setQuantityChangeCount] = useState(0)

  const handleChangeQuantityWageRepairAccessory = (e, index) => {
    setQuantityChangeCount((prevCount) => prevCount + 1)
    const newObject = { ...orderServicePrev }
    const temp = JSON.parse(JSON.stringify(newObject.repairs[index].accessory))
    temp.quantity = +e.target.value
    temp.amount = +e.target.value * temp.price
    const action = setRepairAccessory({ index, newObject: temp })
    dispatch(action)
  }

  const handleChangeQuantityRepair = (e, index) => {
    const newObject = { ...orderServicePrev }
    const temp = JSON.parse(JSON.stringify(newObject.repairSelects[index]))

    temp.quantity = +e.target.value
    if (temp.price) {
      temp.amount = +e.target.value * temp.price
    }
    const action = setRepairItem({ index, newObject: temp })
    dispatch(action)
    setIsQuantityValid(false)
  }

  const handleChangePriceRepair = (e, index) => {
    const newObject = { ...orderServicePrev }
    const temp = JSON.parse(JSON.stringify(newObject.repairSelects[index]))
    temp.price = +e.target.value
    if (temp.quantity) {
      temp.amount = +e.target.value * temp.quantity
    }

    const action = setRepairItem({ index, newObject: temp })
    dispatch(action)
    setIsPriceValid(false)
  }

  const [totalAmoutService, setTotalAmoutService] = useState(
    +orderServicePrev.maintenance.maintenanceByIds.quantityWage *
      +orderServicePrev.maintenance.maintenanceByIds.priceWage
  )

  const getTotalAmoutService = (orderServicePrev) => {
    let totalAmount = 0
    if (
      orderServicePrev &&
      orderServicePrev.maintenance &&
      orderServicePrev.maintenance.maintenanceByIds
    ) {
      if (paymentMaintenance !== 'NB') {
        totalAmount +=
          +orderServicePrev.maintenance.maintenanceByIds.quantityWage *
          +orderServicePrev.maintenance.maintenanceByIds.priceWage
      }
    }
    if (orderServicePrev.repairSelects.length) {
      orderServicePrev.repairSelects.map((item) => {
        if (item.payment !== 'NB') {
          totalAmount += +item.amount
        }
      })
    }
    setTotalAmoutService(totalAmount)
  }

  const [totalAmoutAccessory, setTotalAmoutAccessory] = useState(0)
  const getTotalAmoutAccessory = (orderServicePrev) => {
    let totalAmout = 0
    if (orderServicePrev) {
      if (orderServicePrev.maintenance.accessories.length) {
        orderServicePrev.maintenance.accessories.map((item) => {
          if (item.accessory.payment !== 'NB')
            totalAmout += +item.accessory.amount
        })
      }
      if (orderServicePrev.repairs.length) {
        orderServicePrev.repairs.map((item) => {
          if (item.accessory.payment !== 'NB')
            totalAmout += +item.accessory.amount
        })
      }
    }

    setTotalAmoutAccessory(totalAmout)
  }

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const handleNavitageDone = () => {
    navigate('/reception/car-queues')
  }

  const [loadingButton, setLoadingButton] = useState(false)

  const handleCreateOrder = () => {
    setLoadingButton(true)
    if (
      (isUnitValid &&
        isPaymentValid &&
        isQuantityValid &&
        isPriceValid &&
        isQuantityAccessoryValid) ||
      (!isUnitValid &&
        !isPaymentValid &&
        !isQuantityValid &&
        !isPriceValid &&
        !isQuantityAccessoryValid) ||
      (isUnitValid &&
        isPaymentValid &&
        !isQuantityAccessoryValid &&
        isPriceValid &&
        isQuantityValid)
    ) {
      const accessoriesMaintenance = []
      const repairs = []
      orderServicePrev.maintenance.accessories.map((item) => {
        accessoriesMaintenance.push({
          id: item.accessory.id,
          quantity: item.accessory.quantity,
        })
      })

      orderServicePrev.repairSelects.map((item) => {
        const accessoryRepairs = []
        orderServicePrev.repairs.map((itemRepair) => {
          if (itemRepair.repairItem.id === item.id) {
            accessoryRepairs.push({
              id: itemRepair.accessory.id,
              quantity: itemRepair.accessory.quantity,
            })
          }
        })
        repairs.push({
          repairItemId: item.id,
          unitWage: item.unit,
          priceWage: item.price,
          payment: item.payment,
          quantity: item.quantity,
          accessories: accessoryRepairs,
        })
      })

      const actionCreateOrder = createOrderService({
        carQueueId: orderServicePrev.carQueue.id,
        maintenance: {
          maintenanceId: orderServicePrev.maintenance.maintenanceId,
          payment: paymentMaintenance,
          items: orderServicePrev.maintenance.items,
          accessories: accessoriesMaintenance,
        },
        repairs: repairs,
        timeMinute: orderServicePrev.timeMinute,
        distance: orderServicePrev.distance,
        customerReq: orderServicePrev.reqCustomer,
        doEarly: orderServicePrev.doEarly,
      })

      dispatch(actionCreateOrder)
        .unwrap()
        .then((data) => {
          socket.emit('get-all-order-service', accessToken)
          ToastUtils.showToastSuccessMessage('Tạo mới đơn sửa chữa thành công!')
          setIsCreatingOrder(true)
          debounce(handleNavitageDone, 2000)
        })
        .catch((error) => {
          ToastUtils.showToastFailMessage('Vui lòng điền đầy đủ các trường')
          setIsCreatingOrder(false)
          setLoadingButton(false)
        })
    } else {
      ToastUtils.showToastFailMessage('Vui lòng điền đầy đủ các trường')
      setLoadingButton(false)
    }
  }

  const [isCreatingOrder, setIsCreatingOrder] = useState(false)

  const [isUnitValid, setIsUnitValid] = useState(true)
  const [isPaymentValid, setIsPaymentValid] = useState(true)
  const [isQuantityValid, setIsQuantityValid] = useState(true)
  const [isPriceValid, setIsPriceValid] = useState(true)
  const [isQuantityAccessoryValid, setIsQuantityAccessoryValid] = useState(true)

  useEffect(() => {
    getEnumePayment()
  }, [])

  useEffect(() => {
    // if (orderServicePrev && orderServicePrev.repairs.length)
    if (quantityChangeCount === orderServicePrev.repairs.length) {
      setIsQuantityAccessoryValid(false)
    }
  }, [quantityChangeCount, orderServicePrev.repairs.length])

  useEffect(() => {
    getTotalAmoutService(orderServicePrev)
    getTotalAmoutAccessory(orderServicePrev)
  }, [orderServicePrev])

  const componentRef = useRef(null)

  const [printing, setPrinting] = useState(false)

  const handleBeforePrint = () => {
    setPrinting(true)
  }

  const handleAfterPrint = () => {
    setPrinting(false)
  }

  return (
    <>
      <Box m="20px" pb="20px" ref={componentRef}>
        <ReactToPrint
          trigger={() => (
            <Box
              sx={{
                display: 'flex',
                justifyContent: 'flex-end',
                padding: '8px 16px',
                borderTop: '1px solid',
                borderColor: theme.palette.divider,
                backgroundColor: theme.palette.background.paper,
              }}
            >
              <Button
                variant="contained"
                color="primary"
                className="print-button"
              >
                In lệnh sửa chữa
              </Button>
            </Box>
          )}
          content={() => componentRef.current}
          onBeforePrint={handleBeforePrint}
          onAfterPrint={handleAfterPrint}
        />
        <Grid container md={11} mx={5} border={1}>
          <ToastContainer autoClose={true} />
          <Grid md={3} border={1}>
            <Box
              component="img"
              sx={{
                height: 167,
                width: 250,
              }}
              alt="The house from the offer."
              src={Images.LOGO}
            />
          </Grid>
          <Grid md={9} border={0} marginTop={5} padding={1}>
            <Typography
              variant="h4"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold' }}
            >
              CÔNG TY TNHH TOYOTA HUẾ
            </Typography>
            <Typography
              variant="subtitle1"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold' }}
            >
              Lô C, Khu đô thị Phú Mỹ An, Đường Tố Hữu, Phường An Đông, TP. Huế
            </Typography>
            <Typography
              variant="h6"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold' }}
            >
              Hotline: 0932.555.266
            </Typography>
            <Typography
              variant="h6"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold' }}
            >
              Fax:
            </Typography>
          </Grid>
        </Grid>
        <Grid container md={11} mx={5} border={1}>
          <Grid md={2} border={1}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold', fontSize: '1rem' }}
            >
              SỐ RO:
            </Typography>
          </Grid>
          <Grid md={8} border={1}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="h3"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold', fontSize: '3rem' }}
            >
              LỆNH SỬA CHỮA
            </Typography>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="h5"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold', fontSize: '1rem' }}
            >
              Tư vấn dịch vụ :
            </Typography>

            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="h5"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold', fontSize: '1rem' }}
            >
              (+)
            </Typography>
          </Grid>
          <Grid md={2} border={1}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold', fontSize: '1rem' }}
            >
              Ngày :{/* {currentDate} */}
            </Typography>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              sx={{ fontWeight: 'bold', fontSize: '1rem' }}
            >
              SỐ BCKT:
            </Typography>
          </Grid>
        </Grid>
        <Grid container md={11} mx={5}>
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table" border={1}>
              <TableHead>
                <TableRow>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Khách hàng:
                    </Typography>
                  </TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(orderServicePrev &&
                      orderServicePrev.carPlate.customerName) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Biển số xe:
                    </Typography>
                  </TableCell>
                  <TableCell width={'30%'}>
                    {(orderServicePrev &&
                      orderServicePrev.carPlate.carPlate &&
                      Helper.formatCarNumberPlate(
                        orderServicePrev.carPlate.carPlate
                      )) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Địa chỉ:
                    </Typography>{' '}
                  </TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(orderServicePrev && orderServicePrev.carPlate.address) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Loại xe:
                    </Typography>
                  </TableCell>
                  <TableCell width={'30%'}>
                    {(orderServicePrev && orderServicePrev.carPlate.carName) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Điện thoại:
                    </Typography>
                  </TableCell>
                  <TableCell width={'25'}>
                    {(orderServicePrev &&
                      orderServicePrev.carPlate.phone &&
                      Helper.formatPhoneNumber(
                        orderServicePrev.carPlate.phone
                      )) ||
                      ''}
                  </TableCell>
                  <TableCell width={'25'}>
                    <Typography sx={{ fontWeight: 'bold' }}>MST:</Typography>{' '}
                  </TableCell>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Số khung:
                    </Typography>
                  </TableCell>
                  <TableCell width={'30%'}></TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>Email:</Typography>{' '}
                  </TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(orderServicePrev && orderServicePrev.carPlate.email) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>Số máy:</Typography>
                  </TableCell>
                  <TableCell width={'30%'}></TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Người liên hệ
                    </Typography>
                  </TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(orderServicePrev && orderServicePrev.carQueue.fullName) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Ngày mua:
                    </Typography>
                  </TableCell>
                  <TableCell width={'30%'}>
                    {(orderServicePrev &&
                      orderServicePrev.carPlate.dateBuyCar &&
                      moment(orderServicePrev.carPlate.dateBuyCar).format(
                        'L'
                      )) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Điện thoại:
                    </Typography>{' '}
                  </TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(orderServicePrev &&
                      orderServicePrev.carQueue.phone &&
                      Helper.formatPhoneNumber(
                        orderServicePrev.carQueue.phone
                      )) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>
                    <Typography sx={{ fontWeight: 'bold' }}>Số Km:</Typography>
                  </TableCell>
                  <TableCell width={'30%'}>
                    {(orderServicePrev && orderServicePrev.distance) || null}
                  </TableCell>
                </TableRow>
              </TableHead>
            </Table>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell width={'25%'}>
                    Ngày tiếp nhận:{' '}
                    {(orderServicePrev && orderServicePrev.currentDate) || ''}
                  </TableCell>
                  <TableCell width={'25%'}>
                    Bắt đầu sửa:{' '}
                    {(orderServicePrev && orderServicePrev.dateStart) || ''}
                  </TableCell>
                  <TableCell width={'25%'}>
                    Ngày dự kiến HT:{' '}
                    {(orderServicePrev &&
                      orderServicePrev.currentDateExpected) ||
                      ''}
                  </TableCell>
                  <TableCell width={'25%'}>Ngày giao xe: </TableCell>
                </TableRow>
              </TableHead>
            </Table>
            <Table sx={{ minWidth: 650 }} aria-label="simple table" border={1}>
              <TableHead>
                <TableRow>
                  <TableCell width={'100%'}>
                    Yêu cầu khách hàng:
                    {(orderServicePrev && orderServicePrev.reqCustomer) || ''}
                  </TableCell>
                </TableRow>
              </TableHead>
            </Table>

            <Table sx={{ minWidth: 650 }} aria-label="simple txable" border={1}>
              <TableHead>
                <TableRow>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}> MÃ SỐ</Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>
                      NỘI DUNG CÔNG VIỆC
                    </Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>LHSC</Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>HTTT</Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>ĐVT</Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>SL</Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>
                      {' '}
                      ĐƠN GIÁ (chưa VAT)
                    </Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>
                      {' '}
                      GIẢM GIÁ (%)
                    </Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>
                      {' '}
                      MIỄN PHÍ
                    </Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>
                      {' '}
                      THÀNH TIỀN
                    </Typography>
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th">
                    {' '}
                    <Typography sx={{ fontWeight: 'bold' }}>
                      CÔNG LAO ĐỘNG
                    </Typography>
                  </TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                </TableRow>
                {orderServicePrev &&
                  orderServicePrev.maintenance.maintenanceByIds && (
                    <TableRow
                      key={orderServicePrev.maintenance.maintenanceByIds.id}
                    >
                      <TableCell component="th" align="center">
                        {orderServicePrev.maintenance.maintenanceByIds.id}
                      </TableCell>
                      <TableCell component="th">
                        {orderServicePrev.maintenance.maintenanceByIds.title}
                      </TableCell>
                      <TableCell component="th" align="center">
                        BDĐK
                      </TableCell>
                      <TableCell component="th" align="center">
                        <Select
                          border="none"
                          value={paymentMaintenance}
                          label="HTTT"
                          onChange={handleChangePaymentMaintenance}
                        >
                          {payments.map((item, index) => {
                            return (
                              <MenuItem key={index} value={item}>
                                {item}
                              </MenuItem>
                            )
                          })}
                        </Select>
                      </TableCell>
                      <TableCell component="th" align="center">
                        {orderServicePrev &&
                          orderServicePrev.maintenance.maintenanceByIds
                            .unitWage}
                      </TableCell>
                      <TableCell component="th" align="center">
                        {
                          orderServicePrev.maintenance.maintenanceByIds
                            .quantityWage
                        }
                      </TableCell>
                      <TableCell component="th" align="center">
                        {
                          orderServicePrev.maintenance.maintenanceByIds
                            .priceWage
                        }
                      </TableCell>
                      <TableCell component="th" align="center">
                        {/* <TextField
                        type="text"
                        variant="outlined"
                        onChange={(e) => handleChangeFees(e)}
                        value={fees}
                        sx={{ m: '2px' }}
                      /> */}
                      </TableCell>
                      <TableCell component="th" align="center">
                        {/* <TextField
                        type="text"
                        variant="outlined"
                        onChange={(e) => handleChangeDiscount(e)}
                        value={discount}
                        sx={{ m: '2px' }}
                      /> */}
                      </TableCell>
                      <TableCell component="th" align="center">
                        {paymentMaintenance === 'KHTT'
                          ? orderServicePrev.maintenance.maintenanceByIds
                              .priceWage &&
                            orderServicePrev.maintenance.maintenanceByIds
                              .quantityWage &&
                            Helper.formatCurrency(
                              orderServicePrev.maintenance.maintenanceByIds
                                .priceWage *
                                orderServicePrev.maintenance.maintenanceByIds
                                  .quantityWage
                            )
                          : 0}
                      </TableCell>
                    </TableRow>
                  )}
                {orderServicePrev &&
                  orderServicePrev.repairSelects &&
                  orderServicePrev.repairSelects.map((item, index) => {
                    return (
                      <TableRow key={item.id}>
                        <TableCell component="th" align="center">
                          {item.id}
                        </TableCell>
                        <TableCell component="th">{item.title}</TableCell>
                        <TableCell component="th" align="center">
                          GMGĐ
                        </TableCell>
                        <TableCell component="th" align="center">
                          <Select
                            border="none"
                            value={item.payment || ''}
                            label="HTTT"
                            onChange={(e) =>
                              handleChangePaymentRepair(e, index)
                            }
                            helperText={isPaymentValid ? 'Yêu cầu nhập*' : ''}
                            error={isPaymentValid}
                          >
                            {payments.map((item1, index1) => {
                              return (
                                <MenuItem key={index1} value={item1}>
                                  {item1}
                                </MenuItem>
                              )
                            })}
                          </Select>
                        </TableCell>
                        <TableCell component="th" align="center">
                          <Select
                            border="none"
                            value={item.unit || ''}
                            label="HTTT"
                            onChange={(e) => handleChangeUnitRepair(e, index)}
                            helperText={isUnitValid ? 'Yêu cầu nhập*' : ''}
                            error={isUnitValid}
                          >
                            <MenuItem value="GÓI">GÓI</MenuItem>
                            <MenuItem value="CÔNG">CÔNG</MenuItem>
                          </Select>
                        </TableCell>
                        <TableCell component="th" align="center">
                          <TextField
                            type="text"
                            variant="outlined"
                            value={item.quantity || 0}
                            onChange={(e) =>
                              handleChangeQuantityRepair(e, index)
                            }
                            sx={{ m: '2px' }}
                            helperText={isQuantityValid ? '' : ''}
                            error={isQuantityValid}
                          />
                        </TableCell>
                        <TableCell component="th" align="center">
                          <TextField
                            type="text"
                            variant="outlined"
                            value={item.price || 0}
                            onChange={(e) => handleChangePriceRepair(e, index)}
                            sx={{ m: '2px' }}
                            helperText={isPriceValid ? '' : ''}
                            error={isPriceValid}
                          />
                        </TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center">
                          {item.payment === 'KHTT'
                            ? item.amount && Helper.formatCurrency(item.amount)
                            : 0}
                        </TableCell>
                      </TableRow>
                    )
                  })}

                <TableRow>
                  <TableCell colSpan={9}>Cộng</TableCell>
                  <TableCell align="center">
                    {(totalAmoutService &&
                      Helper.formatCurrency(totalAmoutService)) ||
                      0}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" colSpan={9}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      VẬT TƯ GÓI DỊCH VỤ
                    </Typography>
                  </TableCell>
                </TableRow>
                {orderServicePrev &&
                  orderServicePrev.maintenance.accessories &&
                  orderServicePrev.maintenance.accessories.map(
                    (item, index) => {
                      return (
                        <TableRow key={item.accessory.id}>
                          <TableCell component="th" align="center">
                            {item.accessory.code}
                          </TableCell>
                          <TableCell component="th">
                            {item.accessory.name}
                          </TableCell>
                          <TableCell component="th" align="center">
                            BDĐK
                          </TableCell>
                          <TableCell component="th" align="center">
                            <Select
                              border="none"
                              value={item.accessory.payment || 'KHTT'}
                              defaultValue={item.accessory.payment || 'KHTT'}
                              label="HTTT"
                              onChange={(e) =>
                                handleChangePaymentMaintenanceAccessory(
                                  e,
                                  index
                                )
                              }
                            >
                              {payments.map((item, index) => {
                                return (
                                  <MenuItem key={index} value={item}>
                                    {item}
                                  </MenuItem>
                                )
                              })}
                            </Select>
                          </TableCell>
                          <TableCell component="th" align="center">
                            {item.accessory.unit}
                          </TableCell>
                          <TableCell component="th" align="center">
                            <TextField
                              type="text"
                              variant="outlined"
                              value={item.accessory.quantity || 0}
                              onChange={(e) =>
                                handleChangeQuantityWageMaintenanceAccessory(
                                  e,
                                  index
                                )
                              }
                              helperText={isQuantityValid ? '' : ''}
                              error={isQuantityValid}
                              sx={{ m: '2px' }}
                            />
                          </TableCell>
                          <TableCell component="th" align="center">
                            {item.accessory.price || 0}
                          </TableCell>
                          <TableCell component="th" align="center"></TableCell>
                          <TableCell component="th" align="center"></TableCell>
                          <TableCell component="th" align="center">
                            {item.accessory.payment === 'KHTT'
                              ? item.accessory.amount &&
                                Helper.formatCurrency(item.accessory.amount)
                              : 0}
                          </TableCell>
                        </TableRow>
                      )
                    }
                  )}
                {orderServicePrev &&
                  orderServicePrev.repairs &&
                  orderServicePrev.repairs.map((item, index) => {
                    return (
                      <TableRow key={item.accessory.id}>
                        <TableCell component="th" align="center">
                          {item.accessory.code}
                        </TableCell>
                        <TableCell component="th">
                          {item.accessory.name}
                        </TableCell>
                        <TableCell component="th" align="center">
                          GMGĐ
                        </TableCell>
                        <TableCell component="th" align="center">
                          <Select
                            border="none"
                            value={item.accessory.payment || 'KHTT'}
                            defaultValue={item.accessory.payment || 'KHTT'}
                            label="HTTT"
                            onChange={(e) =>
                              handleChangePaymentRepairAccessory(e, index)
                            }
                          >
                            {payments.map((item, index1) => {
                              return (
                                <MenuItem key={index1} value={item}>
                                  {item}
                                </MenuItem>
                              )
                            })}
                          </Select>
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.accessory.unit}
                        </TableCell>
                        <TableCell component="th" align="center">
                          <TextField
                            type="text"
                            variant="outlined"
                            value={item.accessory.quantity || 0}
                            onChange={(e) =>
                              handleChangeQuantityWageRepairAccessory(e, index)
                            }
                            sx={{ m: '2px' }}
                            helperText={isQuantityAccessoryValid ? '' : ''}
                            error={isQuantityAccessoryValid}
                          />
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.accessory.price || 0}
                        </TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center">
                          {item.accessory.payment === 'NB'
                            ? 0
                            : item.accessory.amount &&
                              Helper.formatCurrency(item.accessory.amount)}
                        </TableCell>
                      </TableRow>
                    )
                  })}
                <TableRow>
                  <TableCell colSpan={9}>Cộng</TableCell>
                  <TableCell align="center">
                    {(totalAmoutAccessory &&
                      Helper.formatCurrency(totalAmoutAccessory)) ||
                      0}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={5} rowSpan={7}>
                    <Typography
                      sx={{ fontWeight: 'bold', marginBottom: '320px' }}
                    >
                      Mục đề nghị làm sớm / Ghi chú :
                    </Typography>
                  </TableCell>
                  <TableCell colSpan={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Tổng cộng:
                    </Typography>
                  </TableCell>
                  <TableCell align="center">
                    {' '}
                    {(+totalAmoutService + +totalAmoutAccessory &&
                      Helper.formatCurrency(
                        totalAmoutService + totalAmoutAccessory
                      )) ||
                      0}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Tổng cộng giảm giá:
                    </Typography>
                  </TableCell>
                  <TableCell align="center">{0}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Tổng cộng sau giảm giá:
                    </Typography>
                  </TableCell>
                  <TableCell align="center">
                    {(+totalAmoutAccessory + +totalAmoutService &&
                      Helper.formatCurrency(
                        +totalAmoutService + +totalAmoutAccessory
                      )) ||
                      0}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Tiền thuế GTGT:
                    </Typography>
                  </TableCell>
                  <TableCell align="center">
                    {(+totalAmoutAccessory + +totalAmoutService &&
                      Helper.formatCurrency(
                        (+totalAmoutService + +totalAmoutAccessory) * 0.08
                      )) ||
                      0}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Miễn phí:
                    </Typography>
                  </TableCell>
                  <TableCell align="center">{0}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Tổng giá trị thanh toán:
                    </Typography>
                  </TableCell>
                  <TableCell align="center">
                    {(+totalAmoutAccessory + +totalAmoutService &&
                      Helper.formatCurrency(
                        +totalAmoutService +
                          +totalAmoutAccessory +
                          (+totalAmoutService + +totalAmoutAccessory) * 0.08
                      )) ||
                      0}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={5}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Bằng chữ:{' '}
                      {(+totalAmoutAccessory +
                        +totalAmoutService +
                        (+totalAmoutAccessory + +totalAmoutService) * 0.08 &&
                        Helper.to_vietnamese(
                          +totalAmoutAccessory +
                            +totalAmoutService +
                            (+totalAmoutAccessory + +totalAmoutService) * 0.08
                        ) + ' đồng') ||
                        ''}
                    </Typography>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
        <Grid container md={11} m={5}>
          <Typography
            variant="h6"
            color={colors.grey[100]}
            sx={{ fontStyle: 'italic' }}
          >
            Lưu ý: Quý khách vui lòng xin mang theo toàn bộ tư trang, tiền bạc,
            vật dụng có giá trị ra khỏi xe. Khách hàng vui lòng không tự ý lái
            xe trong xưởng, nhận lại xe tại khu vực giao xe. Thuế xuất thuế GTGT
            có thể thay đổi theo chính sách thuế tại ngày xuất hóa đơn.
          </Typography>
        </Grid>
        <Grid container md={10} m={5}>
          <Grid md={4}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              fontSize={16}
            >
              Khách hàng
            </Typography>
          </Grid>
          <Grid md={4}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              fontSize={16}
            >
              Tư vấn dịch vụ
            </Typography>
          </Grid>
          <Grid md={4}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              fontSize={16}
            >
              Tổ Trưởng / Quản Đốc
            </Typography>
          </Grid>
        </Grid>
      </Box>
      <Grid md={4} m={7}>
        {/* <Button
          variant="contained"
          type="button"
          onClick={goBack}
          color="warning"
          sx={{
            ml: '20px',
            mb: '20px',
          }}
          disabled={isCreatingOrder}
        >
          Quay lại
        </Button> */}
        <LoadingButton
          variant="contained"
          type="button"
          onClick={handleCreateOrder}
          loading={loadingButton}
          loadingPosition="start"
          color="success"
          sx={{
            ml: '20px',
            mb: '20px',
            width: '50%',
          }}
          disabled={isCreatingOrder}
        >
          Tạo Order
        </LoadingButton>
        <Button
          variant="contained"
          type="button"
          onClick={handleUpdateOrderService}
          color="warning"
          sx={{
            ml: '20px',
            mb: '20px',
          }}
          disabled={isCreatingOrder}
        >
          Chỉnh sửa
        </Button>
      </Grid>
    </>
  )
}

export default OrderServiceDetailPage

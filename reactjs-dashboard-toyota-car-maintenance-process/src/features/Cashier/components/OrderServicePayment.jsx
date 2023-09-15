import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'

import {
  Box,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  useTheme,
} from '@mui/material'

import Images from 'constants/images'
import { tokens } from 'theme'
import {
  getAllOrderMaintenanceItemsDoneByOrderServiceId,
  getAllOrderMaintenancesDoneByOrderServiceId,
  getAllOrderRepairItemsDoneByOrderServiceId,
  getManagementCarPlateByCarPlate,
  getOrderServiceDoneById,
  getTimeCreateOrderService,
  getTimeDoneOrderService,
  paymentOrderService,
} from 'app/cashierSlice'
import moment from 'moment'
import Helper from 'utils/Helper'
import { ToastContainer, toast } from 'react-toastify'
import { LoadingButton } from '@mui/lab'
import BackButton from 'components/Button/BackButton'

const OrderServicePayment = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const params = useParams()
  const orderServiceId = params.orderServiceId
  const goBack = () => {
    navigate(-1)
  }

  const orderServiceDone = useSelector(
    (state) => state.cashier.data.orderServiceDone
  )
  const orderServiceTimeCreate = useSelector(
    (state) => state.cashier.data.orderServiceTimeCreate
  )
  const orderServiceTimeDone = useSelector(
    (state) => state.cashier.data.orderServiceTimeDone
  )

  const managementCarPlate = useSelector(
    (state) => state.cashier.data.managementCarPlate
  )

  const orderMaintenanceDone = useSelector(
    (state) => state.cashier.data.orderMaintenanceDoneByOrderServiceId
  )

  const orderMaintenanceItemsDone = useSelector(
    (state) => state.cashier.data.orderMaintenanceItemsDoneByOrderServiceId
  )

  const orderMaintenanceItemAccessoriesDone = useSelector(
    (state) =>
      state.cashier.data
        .orderMaintenanceItemAccessoriesDoneByOrderMaintenanceItemId
  )

  const orderRepairItemsDone = useSelector(
    (state) => state.cashier.data.orderRepairItemsDoneByOrderServiceId
  )

  const orderRepairItemAccessoriesDone = useSelector(
    (state) =>
      state.cashier.data.orderRepairItemAccessoriesDoneByOrderRepairItemId
  )

  const [amountTotalWage, setAmountTotalWage] = useState('')
  const [amountTotalAccessory, setAmountTotalAccessory] = useState('')

  const handleGetAmoutTotal = () => {
    let amountTotalWage = 0
    let amoutTotalAccessory = 0
    if (orderMaintenanceDone) {
      amountTotalWage += orderMaintenanceDone.amount
    }
    if (orderRepairItemsDone) {
      orderRepairItemsDone.map((item) => {
        amountTotalWage += item.amount
      })
    }
    setAmountTotalWage(amountTotalWage)

    if (orderMaintenanceItemAccessoriesDone.length) {
      orderMaintenanceItemAccessoriesDone.map((item) => {
        amoutTotalAccessory += item.accessoryQuantity * item.accessory.price
      })
    }

    if (orderRepairItemAccessoriesDone.length) {
      orderRepairItemAccessoriesDone.map((item) => {
        amoutTotalAccessory += item.accessoryQuantity * item.accessory.price
      })
    }
    setAmountTotalAccessory(amoutTotalAccessory)
  }

  const handleGetAllInfoOrderSerViceDone = () => {
    dispatch(getOrderServiceDoneById({ id: orderServiceId }))
      .unwrap()
      .then((data) => {
        if (data) {
          if (data.carQueue) {
            dispatch(
              getManagementCarPlateByCarPlate({
                carPlate: data.carQueue.carNumberPlates,
              })
            )
          }
        }
      })

    dispatch(
      getAllOrderMaintenancesDoneByOrderServiceId({ id: orderServiceId })
    )

    dispatch(
      getAllOrderMaintenanceItemsDoneByOrderServiceId({ id: orderServiceId })
    )

    dispatch(getAllOrderRepairItemsDoneByOrderServiceId({ id: orderServiceId }))

    dispatch(getTimeCreateOrderService({ id: orderServiceId }))
    dispatch(getTimeDoneOrderService({ id: orderServiceId }))
  }

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const navigateDone = () => {
    navigate('/cashier/bill-services')
  }

  const [loadingButton, setLoadingButton] = useState(false)

  const handlePaymentOrderService = () => {
    setLoadingButton(true)
    if (managementCarPlate) {
      dispatch(paymentOrderService({ carPlate: managementCarPlate.carPlate }))
        .unwrap()
        .then((data) => {
          showToastMessageSuccess()
          debounce(navigateDone, 2000)
        })
        .catch((error) => {
          showToastFailMessageByJobIsFalse()
          setLoadingButton(false)
        })
    }
  }

  const showToastMessageSuccess = () => {
    toast.success('Thanh toán thành công!', {
      position: toast.POSITION.TOP_RIGHT,
      autoClose: 1500,
      hideProgressBar: false,
      closeOnClick: true,
      draggable: false,
      pauseOnHover: true,
      width: '700px',
      style: {
        fontSize: '16px',
      },
      icon: true,
      closeButton: false,
    })
  }
  const showToastFailMessageByJobIsFalse = () => {
    toast.error('Thanh toán thất bại! Vui lòng kiểm tra lại hóa đơn!', {
      position: toast.POSITION.TOP_RIGHT,
      autoClose: 1500,
      hideProgressBar: false,
      closeOnClick: true,
      draggable: false,
      pauseOnHover: true,
      width: '700px',
      style: {
        fontSize: '16px',
      },
      icon: true,
      closeButton: false,
    })
  }

  useEffect(() => {
    handleGetAllInfoOrderSerViceDone()
    handleGetAmoutTotal()
  }, [])

  return (
    <Box m="20px" pb="20px">
      <Box>
        <ToastContainer autoClose={true} />
        <Grid item container md={10} mx={5}>
          <Grid item md={3}>
            <Box
              component="img"
              sx={{
                height: 167,
                width: 250,
                // maxHeight: { xs: 200, md: 167 },
                // maxWidth: { xs: 300, md: 250 },
              }}
              alt="The house from the offer."
              src={Images.LOGO}
            />
          </Grid>
          <Grid item md={9}>
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
        <Box>
          <Grid item md={9} m={3}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="h4"
              color={colors.grey[100]}
              fontSize={35}
              sx={{ fontWeight: 'bold' }}
            >
              QUYẾT TOÁN SỬA CHỮA
            </Typography>
          </Grid>
        </Box>
        <Grid container item md={10} mx={5}>
          <Grid item md={2}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              fontSize={16}
            >
              SỐ RO:
            </Typography>
          </Grid>
          <Grid item md={8}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="h5"
              color={colors.grey[100]}
              fontSize={14}
            >
              Tư vấn dịch vụ :
            </Typography>

            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="h5"
              color={colors.grey[100]}
              fontSize={14}
            >
              (+)
            </Typography>
          </Grid>
          <Grid item md={2}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              fontSize={16}
            >
              Ngày : {moment().format('L')}
            </Typography>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              fontSize={16}
            >
              SỐ BCKT:
            </Typography>
          </Grid>
        </Grid>
        <Grid container item md={10} mx={5}>
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table" border={1}>
              <TableHead>
                <TableRow>
                  <TableCell width={'10%'}>Khách hàng:</TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(managementCarPlate && managementCarPlate.customerName) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>Biển số xe:</TableCell>
                  <TableCell width={'30%'}>
                    {(managementCarPlate &&
                      managementCarPlate.carPlate &&
                      Helper.formatCarNumberPlate(
                        managementCarPlate.carPlate
                      )) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>Địa chỉ: </TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(managementCarPlate &&
                      managementCarPlate.customer &&
                      managementCarPlate.customer.customerLocation.address +
                        ', ' +
                        managementCarPlate.customer.customerLocation.wardName +
                        ', ' +
                        managementCarPlate.customer.customerLocation
                          .provinceName) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>Loại xe:</TableCell>
                  <TableCell width={'30%'}>
                    {(managementCarPlate &&
                      managementCarPlate.car &&
                      managementCarPlate.car.vehicle &&
                      managementCarPlate.car.vehicle.name) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>Điện thoại:</TableCell>
                  <TableCell width={'25'}>
                    {(managementCarPlate && managementCarPlate.phone) || ''}
                  </TableCell>
                  <TableCell width={'25'}>MST: </TableCell>
                  <TableCell width={'10%'}>Số khung:</TableCell>
                  <TableCell width={'30%'}></TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>Email: </TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(managementCarPlate && managementCarPlate.email) || ''}
                  </TableCell>
                  <TableCell width={'10%'}>Số máy:</TableCell>
                  <TableCell width={'30%'}></TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>Người liên hệ</TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(managementCarPlate && managementCarPlate.customerName) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>Ngày mua:</TableCell>
                  <TableCell width={'30%'}>
                    {(managementCarPlate &&
                      managementCarPlate.customer &&
                      moment(managementCarPlate.customer.createdAt).format(
                        'L'
                      )) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell width={'10%'}>Điện thoại: </TableCell>
                  <TableCell width={'50%'} colSpan={2}>
                    {(managementCarPlate && managementCarPlate.phone) || ''}
                  </TableCell>
                  <TableCell width={'10%'}>Số Km:</TableCell>
                  <TableCell width={'30%'}>
                    {(orderServiceDone && orderServiceDone.distance) || null}
                  </TableCell>
                </TableRow>
              </TableHead>
            </Table>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell width={'25%'}>
                    Ngày tiếp nhận:{' '}
                    {(orderServiceTimeCreate &&
                      orderServiceTimeCreate[0] &&
                      moment(orderServiceTimeCreate[0].createdAt).format(
                        'L LT'
                      )) ||
                      ''}
                  </TableCell>
                  <TableCell width={'25%'}>
                    Bắt đầu sửa:{' '}
                    {(orderServiceTimeCreate &&
                      orderServiceTimeCreate[1] &&
                      moment(orderServiceTimeCreate[1].createdAt)
                        .add(5, 'minutes')
                        .format('L LT')) ||
                      ''}
                  </TableCell>
                  <TableCell width={'25%'}>
                    Hoàn thành:{' '}
                    {(orderServiceTimeDone &&
                      moment(orderServiceTimeDone.createdAt).format('L LT')) ||
                      ''}
                  </TableCell>
                  <TableCell width={'25%'}>
                    Ngày giao xe:{' '}
                    {(orderServiceTimeDone &&
                      moment(orderServiceTimeDone.createdAt).format('L LT')) ||
                      ''}
                  </TableCell>
                </TableRow>
              </TableHead>
            </Table>
            <Table sx={{ minWidth: 650 }} aria-label="simple table" border={1}>
              <TableHead>
                <TableRow>
                  <TableCell width={'100%'}>
                    Yêu cầu khách hàng:{' '}
                    {(orderServiceDone && orderServiceDone.reqCustomer) || ''}
                  </TableCell>
                </TableRow>
              </TableHead>
            </Table>

            <Table sx={{ minWidth: 650 }} aria-label="simple txable" border={1}>
              <TableHead>
                <TableRow>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>MÃ SỐ</Typography>
                  </TableCell>
                  <TableCell component="th" align="left">
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
                      ĐƠN GIÁ (chưa VAT)
                    </Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>
                      GIẢM GIÁ (%)
                    </Typography>
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>MÃ SỐ</Typography>
                    MIỄN PHÍ
                  </TableCell>
                  <TableCell component="th" align="center">
                    <Typography sx={{ fontWeight: 'bold' }}>MÃ SỐ</Typography>
                    THÀNH TIỀN
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th">CÔNG LAO ĐỘNG</TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" align="center"></TableCell>
                </TableRow>
                {orderMaintenanceDone && orderMaintenanceDone.maintenance && (
                  <TableRow>
                    <TableCell component="th" align="center">
                      {orderMaintenanceDone.id}
                    </TableCell>
                    <TableCell component="th">
                      {orderMaintenanceDone.name}
                    </TableCell>
                    <TableCell component="th" align="center">
                      {orderMaintenanceDone.packName}
                    </TableCell>
                    <TableCell component="th" align="center">
                      {orderMaintenanceDone.payment}
                    </TableCell>
                    <TableCell component="th" align="center">
                      {orderMaintenanceDone.unitWage}
                    </TableCell>
                    <TableCell component="th" align="center">
                      {orderMaintenanceDone.quantity &&
                        Helper.formatQuantity(orderMaintenanceDone.quantity)}
                    </TableCell>
                    <TableCell component="th" align="center">
                      {orderMaintenanceDone.priceWage &&
                        Helper.formatCurrency(orderMaintenanceDone.priceWage)}
                    </TableCell>
                    <TableCell component="th" align="center">
                      {orderMaintenanceDone.fees || ''}
                    </TableCell>
                    <TableCell component="th" align="center">
                      {(orderMaintenanceDone.discount &&
                        Helper.formatCurrency(orderMaintenanceDone.discount)) ||
                        ''}
                    </TableCell>
                    <TableCell component="th" align="center">
                      {(orderMaintenanceDone.amount &&
                        Helper.formatCurrency(orderMaintenanceDone.amount)) ||
                        ''}
                    </TableCell>
                  </TableRow>
                )}

                {orderRepairItemsDone &&
                  orderRepairItemsDone.map((item, index) => {
                    return (
                      <TableRow key={index}>
                        <TableCell component="th" align="center">
                          {item.id}
                        </TableCell>
                        <TableCell component="th">{item.name}</TableCell>
                        <TableCell component="th" align="center">
                          {item.packName}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.payment}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.unitWage}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.quantity &&
                            Helper.formatQuantity(item.quantity)}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.priceWage &&
                            Helper.formatCurrency(item.priceWage)}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.fees || ''}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {(item.discount &&
                            Helper.formatCurrency(item.discount)) ||
                            ''}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {(item.amount &&
                            Helper.formatCurrency(item.amount)) ||
                            ''}
                        </TableCell>
                      </TableRow>
                    )
                  })}

                <TableRow>
                  <TableCell colSpan={9}>
                    <Typography sx={{ fontWeight: 'bold' }}>Cộng</Typography>
                  </TableCell>
                  <TableCell align="center">
                    {(amountTotalWage &&
                      Helper.formatCurrency(amountTotalWage)) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell component="th" align="center"></TableCell>
                  <TableCell component="th" colSpan={9}>
                    <Typography sx={{ fontWeight: 'bold' }}>VẬT TƯ</Typography>
                  </TableCell>
                </TableRow>
                {orderMaintenanceItemAccessoriesDone &&
                  orderMaintenanceItemAccessoriesDone.map((item, index) => {
                    return (
                      <TableRow key={index}>
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
                          {item.payment}
                        </TableCell>

                        <TableCell component="th" align="center">
                          {item.accessory.unit}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {(item.accessoryQuantity &&
                            Helper.formatQuantity(item.accessoryQuantity)) ||
                            ''}
                        </TableCell>

                        <TableCell component="th" align="center">
                          {(item.accessory.price &&
                            Helper.formatCurrency(item.accessory.price)) ||
                            ''}
                        </TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center">
                          {(item.accessory.price &&
                            item.accessoryQuantity &&
                            Helper.formatCurrency(
                              item.accessory.price * item.accessoryQuantity
                            )) ||
                            ''}
                        </TableCell>
                      </TableRow>
                    )
                  })}
                {orderRepairItemAccessoriesDone &&
                  orderRepairItemAccessoriesDone.map((item, index) => {
                    return (
                      <TableRow key={index}>
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
                          {item.payment}
                        </TableCell>

                        <TableCell component="th" align="center">
                          {item.accessory.unit}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {(item.accessoryQuantity &&
                            Helper.formatQuantity(item.accessoryQuantity)) ||
                            ''}
                        </TableCell>

                        <TableCell component="th" align="center">
                          {(item.accessory.price &&
                            Helper.formatCurrency(+item.accessory.price)) ||
                            ''}
                        </TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center">
                          {(item.accessory.price &&
                            item.accessoryQuantity &&
                            Helper.formatCurrency(
                              +item.accessory.price * +item.accessoryQuantity
                            )) ||
                            ''}
                        </TableCell>
                      </TableRow>
                    )
                  })}

                <TableRow>
                  <TableCell colSpan={9}>
                    <Typography sx={{ fontWeight: 'bold' }}>Cộng</Typography>
                  </TableCell>
                  <TableCell align="center">
                    {(amountTotalAccessory &&
                      Helper.formatCurrency(amountTotalAccessory)) ||
                      ''}
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
                    {(orderServiceDone &&
                      orderServiceDone.totalAmount &&
                      Helper.formatCurrency(orderServiceDone.totalAmount)) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Tiền thuế GTGT:
                    </Typography>
                  </TableCell>
                  <TableCell align="center">
                    {(orderServiceDone &&
                      orderServiceDone.amountTax &&
                      Helper.formatCurrency(orderServiceDone.amountTax)) ||
                      ''}
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
                    {(orderServiceDone &&
                      orderServiceDone.totalAmountAfterTax &&
                      Helper.formatCurrency(
                        orderServiceDone.totalAmountAfterTax
                      )) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={5}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Bằng chữ:{' '}
                      {(orderServiceDone &&
                        orderServiceDone.totalAmountAfterTax &&
                        Helper.to_vietnamese(
                          orderServiceDone.totalAmountAfterTax
                        )) + ' đồng' || ''}
                    </Typography>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
        <Grid container item md={10} m={5}>
          <Typography sx={{ fontWeight: 'bold' }}>
            Lần bảo dưỡng kế tiếp ............km hoặc ngày ...../..../..... Xin
            quý khách hẹn trước với nhân viên Chăm sóc khách hàng hoặc Tư vấn
            dịch Vụ
          </Typography>
        </Grid>
        <Grid container item md={10} m={5}>
          <Typography sx={{ fontStyle: 'italic' }}>
            Phụ tùng thay thế tại trung tâm Dịch vụ ủy quyền của Toyota được bảo
            hành 06 tháng hoặc 10.000 km tùy theo điều kiện nào tới trước.
          </Typography>
        </Grid>
        <Grid container item md={10} m={5}>
          <Typography sx={{ fontStyle: 'italic' }}>
            Phiếu này chỉ có giá trị xuất hóa đơn trong ngày
          </Typography>
        </Grid>
        <Grid container item md={10} m={5}>
          <Grid item md={3}>
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
          <Grid item md={3}>
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
          <Grid item md={3}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              fontSize={16}
            >
              Kế toán dịch vụ
            </Typography>
          </Grid>
          <Grid item md={3}>
            <Typography
              display={'flex'}
              alignItems={'center'}
              justifyContent={'center'}
              variant="subtitle1"
              color={colors.grey[100]}
              fontSize={16}
            >
              TP Dịch vụ
            </Typography>
          </Grid>
        </Grid>
      </Box>

      <Box display="flex" justifyContent="end" m={'20px'}>
        <BackButton onClick={goBack} />
        <LoadingButton
          variant="contained"
          type="button"
          onClick={handlePaymentOrderService}
          color="secondary"
          loading={loadingButton}
          loadingPosition="start"
          sx={{ ml: '10px', minWidth: '150px', minHeight: '35px', mt: '10px' }}
        >
          Thanh toán
        </LoadingButton>
      </Box>
    </Box>
  )
}

export default OrderServicePayment

import React, { useEffect, useState, useRef } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'

import {
  Box,
  Button,
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
  getAllBillServiceDetailAccessoryByBillServiceDetailId,
  getAllBillServiceDetailByBillServiceId,
  getBillServiceById,
  getManagementCarPlateByCarPlate,
  getTimeCreateOrderService,
  getTimeDoneOrderService,
} from 'app/cashierSlice'
import moment from 'moment'
import Helper from 'utils/Helper'
import ReactToPrint from 'react-to-print'
import BackButton from 'components/Button/BackButton'
import AddButton from 'components/Button/AddButton'
import BackListButton from 'components/Button/BackListButton'

const BillServiceDetail = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const params = useParams()
  const billServiceId = params.billServiceId

  const billService = useSelector((state) => state.cashier.data.billService)
  const orderServiceTimeCreate = useSelector(
    (state) => state.cashier.data.orderServiceTimeCreate
  )
  const orderServiceTimeDone = useSelector(
    (state) => state.cashier.data.orderServiceTimeDone
  )

  const managementCarPlate = useSelector(
    (state) => state.cashier.data.managementCarPlate
  )

  const billServiceDetails = useSelector(
    (state) => state.cashier.data.billServiceDetails
  )

  const billServiceDetailAccessories = useSelector(
    (state) => state.cashier.data.billServiceDetailAccessories
  )

  const [amountTotalWage, setAmountTotalWage] = useState('')
  const [amountTotalAccessory, setAmountTotalAccessory] = useState('')

  const handleGetAmoutTotal = () => {
    let amountTotalWage = 0
    let amountTotalAccessory = 0
    if (billServiceDetails.length) {
      billServiceDetails.map((item) => {
        amountTotalWage += item.amount
      })
    }
    setAmountTotalWage(amountTotalWage)

    if (billServiceDetailAccessories.length) {
      billServiceDetailAccessories.map((item) => {
        amountTotalAccessory += item.amount
      })
    }
    setAmountTotalAccessory(amountTotalAccessory)
  }

  const handleGetBillServiceDetail = () => {
    dispatch(getBillServiceById({ id: billServiceId }))
      .unwrap()
      .then((data) => {
        if (data) {
          dispatch(
            getManagementCarPlateByCarPlate({
              carPlate: data.orderService.carQueue.carNumberPlates,
            })
          )
        }
        dispatch(getTimeCreateOrderService({ id: data.orderService.id }))
        dispatch(getTimeDoneOrderService({ id: data.orderService.id }))
      })
    dispatch(getAllBillServiceDetailByBillServiceId({ id: billServiceId }))
      .unwrap()
      .then((data) => {
        if (data.length) {
          data.map((item) => {
            dispatch(
              getAllBillServiceDetailAccessoryByBillServiceDetailId({
                id: item.id,
              })
            )
          })
        }
      })
  }

  const handleBackListBillService = () => {
    navigate('/cashier/bill-services')
  }

  useEffect(() => {
    handleGetBillServiceDetail()
    handleGetAmoutTotal()
  }, [])

  const componentRef = useRef(null)

  const [printing, setPrinting] = useState(false)

  const handleBeforePrint = () => {
    setPrinting(true)
  }

  const handleAfterPrint = () => {
    setPrinting(false)
  }

  const PrintableContent = () => (
    <Box m="20px" pb="20px" ref={componentRef}>
      <Box display="flex" justifyContent="end" m={'20px'}>
        <BackListButton
          text="Danh sách hóa đơn bảo dưỡng, sửa chữa"
          onClick={handleBackListBillService}
        />
      </Box>
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
              In hóa đơn
            </Button>
          </Box>
        )}
        content={() => componentRef.current}
        onBeforePrint={handleBeforePrint}
        onAfterPrint={handleAfterPrint}
      />

      <Box>
        <Grid item container md={11} mx={5}>
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
        <Grid container item md={11} mx={5}>
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
              Ngày : {billService && moment(billService.createdAt).format('L')}
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
        <Grid container item md={11} mx={5}>
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
                    {(billService &&
                      billService?.orderService?.carQueue?.fullName) ||
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
                    {(billService &&
                      billService?.orderService?.carQueue?.phone) ||
                      ''}
                  </TableCell>
                  <TableCell width={'10%'}>Số Km:</TableCell>
                  <TableCell width={'30%'}>
                    {(billService &&
                      billService.orderService &&
                      billService.orderService.distance) ||
                      null}
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
                    {(billService &&
                      billService.orderService &&
                      billService.orderService.reqCustomer) ||
                      ''}
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

                {billServiceDetails &&
                  billServiceDetails.map((item, index) => {
                    return (
                      <TableRow key={index}>
                        <TableCell component="th" align="center">
                          {item.id}
                        </TableCell>
                        <TableCell component="th">{item.serviceName}</TableCell>
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
                {billServiceDetailAccessories &&
                  billServiceDetailAccessories.map((item, index) => {
                    return (
                      <TableRow key={index}>
                        <TableCell component="th" align="center">
                          {item.accessoryCode}
                        </TableCell>
                        <TableCell component="th">
                          {item.accessoryName}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.billServiceDetail.packName}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.billServiceDetail.payment}
                        </TableCell>

                        <TableCell component="th" align="center">
                          {item.accessoryUnit}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {(item.accessoryQuantity &&
                            Helper.formatQuantity(item.accessoryQuantity)) ||
                            ''}
                        </TableCell>

                        <TableCell component="th" align="center">
                          {(item.accessoryPrice &&
                            Helper.formatCurrency(item.accessoryPrice)) ||
                            ''}
                        </TableCell>
                        <TableCell component="th" align="center"></TableCell>
                        <TableCell component="th" align="center"></TableCell>
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
                    {(billService &&
                      billService.totalAmount &&
                      Helper.formatCurrency(billService.totalAmount)) ||
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
                    {(billService &&
                      billService.amountTax &&
                      Helper.formatCurrency(billService.amountTax)) ||
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
                    {(billService &&
                      billService.totalAmountAfterTax &&
                      Helper.formatCurrency(billService.totalAmountAfterTax)) ||
                      ''}
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell colSpan={5}>
                    <Typography sx={{ fontWeight: 'bold' }}>
                      Bằng chữ:{' '}
                      {(billService &&
                        billService.totalAmountAfterTax &&
                        Helper.to_vietnamese(billService.totalAmountAfterTax)) +
                        ' đồng' || ''}
                    </Typography>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
        <Grid container item md={11} m={5}>
          <Typography sx={{ fontWeight: 'bold' }}>
            Lần bảo dưỡng kế tiếp ............km hoặc ngày ...../..../..... Xin
            quý khách hẹn trước với nhân viên Chăm sóc khách hàng hoặc Tư vấn
            dịch Vụ
          </Typography>
        </Grid>
        <Grid container item md={11} m={5}>
          <Typography sx={{ fontStyle: 'italic' }}>
            Phụ tùng thay thế tại trung tâm Dịch vụ ủy quyền của Toyota được bảo
            hành 06 tháng hoặc 10.000 km tùy theo điều kiện nào tới trước.
          </Typography>
        </Grid>
        <Grid container item md={11} m={5}>
          <Typography sx={{ fontStyle: 'italic' }}>
            Phiếu này chỉ có giá trị xuất hóa đơn trong ngày
          </Typography>
        </Grid>
        <Grid container item md={11} m={5}>
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

      <Box display="flex" justifyContent="start" m={'20px'}>
        <BackButton onClick={handleBackListBillService} />
      </Box>
    </Box>
  )

  return (
    <Box m="20px" pb="20px">
      <PrintableContent />
      <style>
        {`
        @media print {
          .print-button {
            display: none;
          }
          .back-print {
            display:none;
          }
        }
      `}
      </style>
    </Box>
  )
}

export default BillServiceDetail

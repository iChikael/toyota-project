import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import moment from 'moment'
import 'moment/locale/vi'

import TextField from '@mui/material/TextField'
import Autocomplete from '@mui/material/Autocomplete'
import {
  Box,
  Button,
  Checkbox,
  FormControlLabel,
  FormLabel,
  Grid,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Modal,
  IconButton,
  DialogTitle,
  Typography,
} from '@mui/material'
import HighlightOffIcon from '@mui/icons-material/HighlightOff'
import CloseIcon from '@mui/icons-material/Close'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer, toast } from 'react-toastify'

import {
  getAllCarPlates,
  getAllMaintenances,
  getAllMaintenanceItem,
  getAllMaintenanceMaintenanceItemsByMaintenanceId,
  getAllRepairItems,
  getAllMaintenanceItemAccessoryByMaintenanceItemId,
  getAllRepairItemAccessoryByRepairItemId,
  setOrderServicePrev,
} from 'app/receptionSlice'

import { getAllCarQueue } from 'app/carQueueSlice'
import { LoadingButton } from '@mui/lab'
import ToastUtils from 'utils/ToastUtils'

const SelectServicePage = () => {
  moment.locale('vi')
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const orderServicePrev = useSelector(
    (state) => state.reception.data.orderServicePrev
  )

  const maintenances = useSelector((state) => state.reception.data.maintenances)
  const repairItems = useSelector((state) => state.reception.data.repairItems)
  const maintenanceMaintenanceItems = useSelector(
    (state) => state.reception.data.maintenanceMaintenanceItems
  )
  const maintenanceItemAccessories = useSelector(
    (state) => state.reception.data.maintenanceItemAccessories
  )

  const [distance, setDistance] = useState(
    (orderServicePrev && orderServicePrev.distance) || ''
  )
  const [timeMinute, setTimeMinute] = useState(
    (orderServicePrev && orderServicePrev.timeMinute) || ''
  )
  const [maintenanceId, setMaintenanceId] = useState(
    orderServicePrev &&
      orderServicePrev.maintenance &&
      orderServicePrev.maintenance.maintenanceId
  )
  const [maintenanceByIds, setMaintenanceByIds] = useState(
    orderServicePrev &&
      orderServicePrev.maintenance &&
      orderServicePrev.maintenance.maintenanceByIds
  )
  const [maintenanceItemIds, setMaintenanceItemIds] = useState([])
  const [maintenanceItemsChecked, setMaintenanceItemsChecked] = useState(
    (orderServicePrev &&
      orderServicePrev.maintenance &&
      orderServicePrev.maintenance.items) ||
      []
  )
  const [repairs, setRepairs] = useState([])
  const [open, setOpen] = useState(false)

  const handleOpen = () => {
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
  }
  const getMaintenanceItemAccessory = (maintenanceItemIds) => {
    if (maintenanceItemIds.length) {
      maintenanceItemIds.map((item) => {
        dispatch(
          getAllMaintenanceItemAccessoryByMaintenanceItemId({
            maintenanceItemId: item,
          })
        )
      })
    }
  }

  const handleGetAllInfoSelect = () => {
    dispatch(getAllCarPlates())
    dispatch(getAllCarQueue())
    dispatch(getAllMaintenances())
    dispatch(getAllMaintenanceItem())
    dispatch(getAllRepairItems())
  }

  const handleChangeMaintenance = (e, v) => {
    setMaintenanceId(v.id)
    setMaintenanceByIds(v)
    setIsRepairValid(true)
    setIsCheck(false)
  }

  const handleGetAllMaintenanceMaintenanceItemsByMaintenanceId = (
    maintenanceId
  ) => {
    dispatch(
      getAllMaintenanceMaintenanceItemsByMaintenanceId({
        maintenanceId: maintenanceId,
      })
    )
  }

  const [repairSelects, setRepairSelects] = useState(
    orderServicePrev && orderServicePrev.repairSelects
  )

  const handleChangeRepair = (e, v) => {
    const repairNews = [...repairSelects]
    repairNews.push(v)
    setRepairSelects(repairNews)
    setIsRepairValid(true)
  }
  const [isRepairValid, setIsRepairValid] = useState(
    (orderServicePrev &&
    orderServicePrev.repairSelects &&
    orderServicePrev.repairSelects.length
      ? true
      : false) ||
      (orderServicePrev &&
      orderServicePrev.maintenance &&
      orderServicePrev.maintenance.maintenanceId
        ? true
        : false)
  )

  const handleDeleteSelectRepair = (id) => {
    const repairNews = repairSelects.filter((item) => item.id !== id)
    setRepairSelects(repairNews)
  }

  const getAllRepairItemAccessory = (repairSelects) => {
    if (repairSelects) {
      repairSelects.map((item) => {
        const keyAction = getAllRepairItemAccessoryByRepairItemId({
          repairItemId: item.id,
        })
        dispatch(keyAction)
          .unwrap()
          .then((data) => {
            setRepairs(data)
          })
          .catch((error) => {
            console.log(error)
          })
      })
    }
  }

  const [checkedAll, setCheckedAll] = useState([true, false])

  const handleChangeAll = (event) => {
    if (event.target.checked) {
      setCheckedAll([event.target.checked, event.target.checked])
      const newChecked = []
      const maintenanceItemIdsNew = []
      const maintenanceItemsCheckedNew = []
      maintenanceMaintenanceItems.map((item, index) => {
        if (item.status !== 'NONE') {
          newChecked.push(index)
          maintenanceItemsCheckedNew.push(item.maintenanceItem.id)
          if (item.status === 'REPLACE') {
            maintenanceItemIdsNew.push(item.maintenanceItem.id)
          }
        }
      })
      setMaintenanceItemsChecked(maintenanceItemsCheckedNew)
      setMaintenanceItemIds(maintenanceItemIdsNew)
      setChecked(newChecked)
      setIsCheck(true)
    } else {
      setCheckedAll([true, false])
      setChecked([])
      setMaintenanceItemIds([])
      setMaintenanceItemsChecked([])
      setIsCheck(false)
    }
  }

  const [checked, setChecked] = useState([1])

  const handleToggle = (value, status, maintenanceItem) => () => {
    const currentIndex = checked.indexOf(value)
    const newChecked = [...checked]
    const maintenanceItemIdsNew = [...maintenanceItemIds]
    const maintenanceItemsCheckedNew = [...maintenanceItemsChecked]

    if (currentIndex === -1) {
      newChecked.push(value)
      maintenanceItemsCheckedNew.push(maintenanceItem.id)
      if (status === 'REPLACE') {
        maintenanceItemIdsNew.push(maintenanceItem.id)
      }
    } else {
      maintenanceItemsCheckedNew.splice(currentIndex, 1)
      newChecked.splice(currentIndex, 1)
      if (status === 'REPLACE') {
        maintenanceItemIdsNew.splice(currentIndex, 1)
      }

      setCheckedAll([true, false])
    }

    let count = 0
    maintenanceMaintenanceItems.map((item) => {
      if (item.status !== 'NONE') {
        count += 1
      }
    })
    if (newChecked.length === count) {
      setCheckedAll([true, true])
    }
    setMaintenanceItemsChecked(maintenanceItemsCheckedNew)
    setMaintenanceItemIds(maintenanceItemIdsNew)
    setChecked(newChecked)
    setIsCheck(true)
  }

  const [reqCustomer, setReqCustomer] = useState(
    (orderServicePrev && orderServicePrev.reqCustomer) || ''
  )
  const handleChangeReqCustomer = (e) => {
    setReqCustomer(e.target.value)
  }
  const [doEarly, setDoEarly] = useState(
    (orderServicePrev && orderServicePrev.doEarly) || ''
  )
  const handleChangeDoEarly = (e) => {
    setDoEarly(e.target.value)
  }

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const handleNavitageDone = () => {
    navigate('/reception/order-services/detail')
  }

  const [loadingButton, setLoadingButton] = useState(false)

  const handleUpdateOrderPre = () => {
    setLoadingButton(true)
    if (isCheck && isDistanceValid && isTimeValid && isRepairValid) {
      const action = setOrderServicePrev({
        carQueue: orderServicePrev && orderServicePrev.carQueue,
        carPlate: orderServicePrev && orderServicePrev.carPlate,
        maintenance: {
          maintenanceId: maintenanceId || '',
          maintenanceByIds: maintenanceByIds || '',
          items: maintenanceItemsChecked,
          accessories: maintenanceItemAccessories,
        },
        repairSelects: repairSelects,
        repairs: repairs,
        timeMinute: timeMinute,
        reqCustomer: reqCustomer,
        doEarly: doEarly,
        distance: distance,
        currentDate: orderServicePrev && orderServicePrev.currentDate,
        dateStart: orderServicePrev && orderServicePrev.dateStart,
        currentDateExpected:
          orderServicePrev && orderServicePrev.currentDateExpected,
      })
      dispatch(action)
      ToastUtils.showToastSuccessMessage('Cập nhật lệnh sửa chữa thành công!')
      debounce(handleNavitageDone, 2000)
    } else {
      setLoadingButton(false)
      if (!isCheck) {
        ToastUtils.showToastFailMessage('Vui lòng chọn một dịch vụ bảo dưỡng !')
      } else {
        ToastUtils.showToastFailMessage('Vui lòng điền đầy đủ các trường !')
      }
    }
  }

  const [isCheck, setIsCheck] = useState(true)

  const [isDistanceValid, setIsDistanceValid] = useState(
    orderServicePrev && orderServicePrev.distance ? true : false
  )
  const [isTimeValid, setIsTimeValid] = useState(
    orderServicePrev && orderServicePrev.timeMinute ? true : false
  )

  const handleInputChange = (event) => {
    const value = event.target.value
    setDistance(value)
    setIsDistanceValid(value !== '')
  }

  const handleTimeChange = (e) => {
    const value = e.target.value
    setTimeMinute(value)
    setIsTimeValid(value !== '')
  }

  useEffect(() => {
    getMaintenanceItemAccessory(maintenanceItemIds)
  }, [maintenanceItemIds])

  useEffect(() => {
    handleGetAllInfoSelect()
  }, [])

  useEffect(() => {
    if (maintenanceId) {
      handleGetAllMaintenanceMaintenanceItemsByMaintenanceId(maintenanceId)
      setChecked([])
      setCheckedAll([true, false])
    }
  }, [maintenanceId])

  useEffect(() => {
    getAllRepairItemAccessory(repairSelects)
  }, [repairSelects])

  return (
    <Grid
      container
      m={3}
      sx={{
        marginLeft: '10px',
        // position: 'inte',
        maxWidth: '98.5%',
      }}
    >
      <Grid item md={12}>
        <ToastContainer autoClose={true} />
        <Grid item md={12} container>
          <Grid item md={6} textAlign="center" border={1} sx={{ pb: '10px' }}>
            <Typography
              sx={{ fontWeight: 'bold', fontSize: '18px', m: '10px' }}
            >
              Thông tin khách hàng
            </Typography>

            <Box
              component="form"
              m={5}
              sx={{
                maxWidth: '100%',
              }}
            >
              <Grid item md={12} container>
                <Grid item md={6} textAlign="center">
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                  >
                    Tên khách hàng
                  </FormLabel>
                  <TextField
                    type="text"
                    variant="outlined"
                    value={
                      orderServicePrev &&
                      orderServicePrev.carPlate &&
                      orderServicePrev.carPlate.customerName
                    }
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ width: '95%' }}
                  />
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                  >
                    Số điện thoại
                  </FormLabel>
                  <TextField
                    type="text"
                    variant="outlined"
                    value={
                      orderServicePrev &&
                      orderServicePrev.carPlate &&
                      orderServicePrev.carPlate.phone
                    }
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ width: '95%' }}
                  />
                </Grid>

                <Grid item md={6} textAlign="center">
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                  >
                    Email
                  </FormLabel>
                  <TextField
                    type="text"
                    variant="outlined"
                    value={
                      orderServicePrev &&
                      orderServicePrev.carPlate &&
                      orderServicePrev.carPlate.email
                    }
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ width: '95%' }}
                  />
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                  >
                    Địa chỉ
                  </FormLabel>
                  <TextField
                    type="text"
                    variant="outlined"
                    value={
                      orderServicePrev &&
                      orderServicePrev.carPlate &&
                      orderServicePrev.carPlate.address
                    }
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ width: '95%' }}
                  />
                </Grid>
              </Grid>
            </Box>
          </Grid>
          <Grid item md={6} textAlign="center" border={1} sx={{ pb: '10px' }}>
            <Typography
              sx={{ fontWeight: 'bold', fontSize: '18px', m: '10px' }}
            >
              Thông tin xe
            </Typography>
            <Box component="form" m={5}>
              {/* <FormControl component="fieldset"> */}
              <Grid item md={12} container>
                <Grid item md={12} textAlign="center">
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                  >
                    Tên xe
                  </FormLabel>
                  <TextField
                    type="text"
                    variant="outlined"
                    value={
                      orderServicePrev &&
                      orderServicePrev.carPlate &&
                      orderServicePrev.carPlate.carName
                    }
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ width: '97%' }}
                  />
                </Grid>
                <Grid item md={6} textAlign="center">
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                  >
                    Biển số xe
                  </FormLabel>
                  <TextField
                    type="text"
                    variant="outlined"
                    value={
                      orderServicePrev &&
                      orderServicePrev.carPlate &&
                      orderServicePrev.carPlate.carPlate
                    }
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ width: '95%' }}
                  />
                </Grid>
                <Grid item md={6} textAlign="center">
                  <FormLabel
                    component="legend"
                    sx={{
                      fontWeight: 'bold',
                      color: 'red',
                    }}
                  >
                    Số km *
                  </FormLabel>
                  <TextField
                    type="number"
                    variant="outlined"
                    required
                    value={distance}
                    helperText={
                      !isDistanceValid
                        ? 'Yêu cầu nhập số km của khách hàng*'
                        : ''
                    }
                    error={!isDistanceValid}
                    onChange={handleInputChange}
                    sx={{ width: '95%' }}
                  />
                </Grid>
              </Grid>
            </Box>
          </Grid>
        </Grid>
        <Grid item md={12} container>
          <Grid item md={12} textAlign="center" border={1}>
            <Typography
              sx={{ fontWeight: 'bold', fontSize: '18px', m: '10px' }}
            >
              Thông tin báo giá
            </Typography>

            <Box component="form" m={5}>
              <Grid item md={12} container>
                <Grid item md={4} textAlign="center">
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                  >
                    Yêu cầu khách hàng
                  </FormLabel>
                  <TextField
                    placeholder="Type in here…"
                    minRows={2}
                    maxRows={5}
                    value={reqCustomer}
                    onChange={handleChangeReqCustomer}
                    sx={{ width: '98%' }}
                  />
                </Grid>
                <Grid item md={4} textAlign="center">
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                  >
                    Mục đề nghị làm sớm
                  </FormLabel>
                  <TextField
                    placeholder="Type in here…"
                    minRows={2}
                    maxRows={5}
                    value={doEarly}
                    onChange={handleChangeDoEarly}
                    sx={{ width: '98%' }}
                  />
                </Grid>
                <Grid item md={4}>
                  <FormLabel
                    component="legend"
                    sx={{ fontWeight: 'bold', color: 'red' }}
                  >
                    Thời gian dự kiến hoàn thành (phút) *
                  </FormLabel>
                  <TextField
                    type="number"
                    variant="outlined"
                    required
                    value={timeMinute}
                    helperText={
                      !isTimeValid ? 'Yêu cầu nhập thời gian hoàn thành*' : ''
                    }
                    error={!isTimeValid}
                    onChange={handleTimeChange}
                    sx={{ width: '98%' }}
                  />
                </Grid>
                <Grid item md={12} container>
                  <Grid item md={6} textAlign="center">
                    <FormLabel
                      component="legend"
                      sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                    >
                      Gói bảo dưỡng
                    </FormLabel>
                    <Autocomplete
                      freeSolo
                      id="free-solo-2-demo"
                      disableClearable
                      options={maintenances || []}
                      value={
                        orderServicePrev &&
                        orderServicePrev.maintenance &&
                        orderServicePrev.maintenance.maintenanceByIds
                      }
                      getOptionLabel={(option) => {
                        return option.title || ''
                      }}
                      onChange={(e, v) => {
                        handleChangeMaintenance(e, v)
                      }}
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          label="Tìm kiếm gói bảo dưỡng"
                          InputProps={{
                            ...params.InputProps,
                            type: 'search',
                          }}
                          value={params}
                          InputLabelProps={{
                            sx: {
                              backgroundColor: 'white',
                            },
                          }}
                        />
                      )}
                    />
                    {maintenanceId && (
                      <>
                        <FormControlLabel
                          label="HẠNG MỤC DỊCH VỤ"
                          control={
                            <Checkbox
                              checked={checkedAll[1]}
                              onChange={handleChangeAll}
                            />
                          }
                          sx={{
                            marginTop: '3px',
                            fontSize: '1rem',
                          }}
                        />
                        <Button
                          onClick={handleOpen}
                          sx={{
                            marginTop: '5px',
                            fontSize: '0.8571428571428571rem',
                            ml: '20px',
                          }}
                          ư
                        >
                          Hiển thị danh sách
                        </Button>
                        <Modal
                          open={open}
                          onClose={handleClose}
                          backdropClick={true}
                        >
                          <Box
                            display="flex"
                            justifyContent="center"
                            alignItems="center"
                            width="80%"
                            height="80%"
                          >
                            <DialogTitle>
                              <IconButton
                                edge="end"
                                color="inherit"
                                onClick={handleClose}
                                aria-label="close"
                              >
                                <CloseIcon />
                              </IconButton>
                            </DialogTitle>
                            <List
                              dense
                              sx={{
                                height: '600px',
                                width: '800px',
                                maxWidth: 800,
                                bgcolor: 'background.paper',
                                overflow: 'auto',
                              }}
                            >
                              {maintenanceMaintenanceItems &&
                                maintenanceMaintenanceItems.map(
                                  (item, index) => {
                                    if (item.status !== 'NONE') {
                                      return (
                                        <ListItem
                                          key={index}
                                          secondaryAction={
                                            <Checkbox
                                              edge="end"
                                              onChange={handleToggle(
                                                index,
                                                item.status,
                                                item.maintenanceItem
                                              )}
                                              checked={
                                                checked.indexOf(index) !== -1
                                              }
                                              inputProps={{
                                                'aria-labelledby': index,
                                              }}
                                            />
                                          }
                                          disablePadding
                                        >
                                          <ListItemButton>
                                            <ListItemText
                                              id={item.id}
                                              primary={
                                                item.status +
                                                ' - ' +
                                                item.maintenanceItem.title
                                              }
                                              sx={{
                                                fontSize: '1rem',
                                              }}
                                            />
                                          </ListItemButton>
                                        </ListItem>
                                      )
                                    }
                                  }
                                )}
                            </List>
                          </Box>
                        </Modal>
                      </>
                    )}
                  </Grid>
                  <Grid item md={6} textAlign="center">
                    <FormLabel
                      component="legend"
                      sx={{ fontWeight: 'bold', maxWidth: '100%' }}
                    >
                      Gói sửa chữa
                    </FormLabel>
                    <Autocomplete
                      freeSolo
                      id="free-solo-2-demo"
                      disableClearable
                      options={repairItems || []}
                      value={
                        orderServicePrev &&
                        orderServicePrev.repairSelects &&
                        orderServicePrev.repairSelects[
                          orderServicePrev.repairSelects.length - 1
                        ]
                      }
                      getOptionLabel={(option) => {
                        return option.title
                      }}
                      onChange={(e, v) => {
                        handleChangeRepair(e, v)
                      }}
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          label="Tìm kiếm gói sửa chữa"
                          InputProps={{
                            ...params.InputProps,
                            type: 'search',
                          }}
                          value={params}
                          error={!isRepairValid}
                          helperText={
                            !isRepairValid
                              ? 'Vui lòng chọn một dịch vụ để tiếp tục'
                              : ''
                          }
                          sx={{
                            position: 'relative',
                          }}
                          FormHelperTextProps={{
                            sx: {
                              position: 'absolute',
                              left: '-167px',
                              bottom: '-40px',
                            },
                          }}
                          InputLabelProps={{
                            sx: {
                              backgroundColor: 'white',
                            },
                          }}
                        />
                      )}
                      sx={{
                        ml: '10px',
                        width: '98%',
                        fontSize: '1rem',
                      }}
                    />

                    <List
                      dense
                      sx={{
                        width: '100%',
                      }}
                    >
                      {repairSelects &&
                        repairSelects.map((item) => (
                          <ListItemButton key={item.id}>
                            <ListItemText
                              id={item.id}
                              primary={item.title}
                              sx={{ m: '2px', fontSize: '1rem' }}
                            />

                            <HighlightOffIcon
                              onClick={() => {
                                handleDeleteSelectRepair(item.id)
                              }}
                              sx={{ m: '2px', fontSize: '1rem' }}
                            />
                          </ListItemButton>
                        ))}
                    </List>
                  </Grid>
                </Grid>
              </Grid>
            </Box>
          </Grid>
        </Grid>
      </Grid>
      <Grid item md={12} sx={{ display: 'flex', justifyContent: 'right' }}>
        <LoadingButton
          variant="contained"
          type="button"
          onClick={handleUpdateOrderPre}
          color="secondary"
          loading={loadingButton}
          loadingPosition="start"
          sx={{
            mt: '15px',
            mb: '15px',
            ml: '10px',
            minWidth: '180px',
            minHeight: '35px',
          }}
        >
          Cập nhật
        </LoadingButton>
      </Grid>
    </Grid>
  )
}

export default SelectServicePage

import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

import Card from '@mui/material/Card'
import CardActions from '@mui/material/CardActions'
import CardContent from '@mui/material/CardContent'
import Typography from '@mui/material/Typography'
import { Grid } from '@mui/material'

import {
  getAllServiceArea,
  updateCurrentCapacityServiceArea,
} from 'app/serviceAreaSlice'

import {
  getAllOrderServiceByCurrentServiceArea,
  updateCurrentServiceAreaOrderService,
  updateStatusCarQueueByOrderService,
  updateStatusMaintenanceItemByServiceArea,
  updateStatusOrderMaintenanceByOrderServiceId,
  updateStatusRepairItemByServiceArea,
} from 'app/technicalSlice'

import { EnumStatusOrderService } from 'constants/EStatusOrderService'
import { ToastContainer, toast } from 'react-toastify'
import { LoadingButton } from '@mui/lab'
import Loading from 'components/Loading'
import ToastUtils from 'utils/ToastUtils'

const ListServiceArea = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()

  const serviceAreas = useSelector(
    (state) => state.serviceArea.data.serviceAreas
  )
  const orderService = useSelector((state) => state.technical.data.orderService)

  const orderMaintenance = useSelector(
    (state) => state.technical.data.orderMaintenanceByOrderServiceId
  )
  const orderMaintenanceItems = useSelector(
    (state) => state.technical.data.orderMaintenanceItemsByOrderServiceId
  )

  const orderRepairItems = useSelector(
    (state) => state.technical.data.orderRepairItemsByOrderServiceId
  )

  const [loading, setLoading] = useState(true)
  const handleGetAllServiceArea = () => {
    const getAllServiceAreaAction = getAllServiceArea()
    dispatch(getAllServiceAreaAction)
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách khu vực sửa chữa, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const navigateCoPaint = () => {
    navigate('/technical/service-areas/co-paint-area')
  }
  const navigateGeneralRepair = () => {
    navigate('/technical/service-areas/general-repair-area')
  }
  const navigateUndercarriage = () => {
    navigate('/technical/service-areas/undercarriage-area')
  }
  const navigateRefrigeration = () => {
    navigate('/technical/service-areas/refrigeration-area')
  }
  const navigateWash = () => {
    navigate('/technical/service-areas/wash-area')
  }

  const [loadingButton, setLoadingButton] = useState(false)

  const handleImportCarToServiceArea = (serviceArea, index) => {
    setLoadingButton(true)
    if (orderService.currentServiceArea) {
      if (
        orderService.currentServiceArea !== serviceArea.name &&
        orderService.currentServiceArea !== null
      ) {
        showToastFailMessageCurrentServiceArea(orderService.currentServiceArea)
        setLoadingButton(false)
      }
      if (orderService.currentServiceArea === serviceArea.name) {
        showToastFailMessageCurrentDifferentServiceArea(
          `Xe đang ở khu vực hiện tại!`
        )
        setLoadingButton(false)
      }
    } else {
      let countStatus = 0
      let count = 0
      if (orderMaintenanceItems.length) {
        orderMaintenanceItems.map((item) => {
          if (
            (item.status === EnumStatusOrderService.STATUS_DOING.statusValue &&
              item.maintenanceItem.serviceArea.id === serviceArea.id) ||
            (item.status === EnumStatusOrderService.STATUS_DONE.statusValue &&
              item.maintenanceItem.serviceArea.id === serviceArea.id)
          ) {
            countStatus += 1
          }
          if (item.maintenanceItem.serviceArea.id === serviceArea.id) {
            count += 1
          }
        })
      }
      if (orderRepairItems.length) {
        orderRepairItems.map((item) => {
          if (
            (item.status === EnumStatusOrderService.STATUS_DOING.statusValue &&
              item.repairItem.serviceArea.id === serviceArea.id) ||
            (item.status === EnumStatusOrderService.STATUS_DONE.statusValue &&
              item.repairItem.serviceArea.id === serviceArea.id)
          ) {
            countStatus += 1
          }
          if (item.repairItem.serviceArea.id === serviceArea.id) {
            count += 1
          }
        })
      }

      if (countStatus === count) {
        showToastFailMessageByJobIsFalse()
        setLoadingButton(false)
      } else {
        if (count !== 0) {
          if (serviceArea.currentCapacity >= 5) {
            showToastFailMessageByFull()
            setLoadingButton(false)
          } else {
            setLoadingButton(true)
            if (serviceArea.id === 1) {
              dispatch(
                updateCurrentCapacityServiceArea({
                  ...serviceArea,
                  currentCapacity: +serviceArea.currentCapacity + 1,
                })
              )

              if (orderMaintenance) {
                dispatch(
                  updateStatusOrderMaintenanceByOrderServiceId({
                    orderServiceId: orderService.id,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              if (orderMaintenanceItems.length) {
                dispatch(
                  updateStatusMaintenanceItemByServiceArea({
                    orderMaintenanceId: orderMaintenance.id,
                    serviceAreaId: 1,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }
              if (orderRepairItems.length) {
                dispatch(
                  updateStatusRepairItemByServiceArea({
                    orderServiceId: orderService.id,
                    serviceAreaId: 1,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              dispatch(
                updateCurrentServiceAreaOrderService({
                  orderServiceId: orderService.id,
                  serviceAreaId: 1,
                })
              )

              dispatch(
                getAllOrderServiceByCurrentServiceArea({
                  serviceAreaId: 1,
                })
              )

              dispatch(
                updateStatusCarQueueByOrderService({
                  orderServiceId: orderService.id,
                  status: EnumStatusOrderService.STATUS_DOING.statusValue,
                })
              )
              showToastMessageSuccess()
              debounce(navigateCoPaint, 2000)
            }
            if (serviceArea.id === 2) {
              dispatch(
                updateCurrentCapacityServiceArea({
                  ...serviceArea,
                  currentCapacity: +serviceArea.currentCapacity + 1,
                })
              )

              if (orderMaintenance) {
                dispatch(
                  updateStatusOrderMaintenanceByOrderServiceId({
                    orderServiceId: orderService.id,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              if (orderMaintenanceItems.length) {
                dispatch(
                  updateStatusMaintenanceItemByServiceArea({
                    orderMaintenanceId: orderMaintenance.id,
                    serviceAreaId: 2,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }
              if (orderRepairItems.length) {
                dispatch(
                  updateStatusRepairItemByServiceArea({
                    orderServiceId: orderService.id,
                    serviceAreaId: 2,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              dispatch(
                updateCurrentServiceAreaOrderService({
                  orderServiceId: orderService.id,
                  serviceAreaId: 2,
                })
              )

              dispatch(
                getAllOrderServiceByCurrentServiceArea({
                  serviceAreaId: 2,
                })
              )

              dispatch(
                updateStatusCarQueueByOrderService({
                  orderServiceId: orderService.id,
                  status: EnumStatusOrderService.STATUS_DOING.statusValue,
                })
              )
              showToastMessageSuccess()
              debounce(navigateGeneralRepair, 2000)
            }
            if (serviceArea.id === 3) {
              dispatch(
                updateCurrentCapacityServiceArea({
                  ...serviceArea,
                  currentCapacity: +serviceArea.currentCapacity + 1,
                })
              )

              if (orderMaintenance) {
                dispatch(
                  updateStatusOrderMaintenanceByOrderServiceId({
                    orderServiceId: orderService.id,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              if (orderMaintenanceItems.length) {
                dispatch(
                  updateStatusMaintenanceItemByServiceArea({
                    orderMaintenanceId: orderMaintenance.id,
                    serviceAreaId: 3,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }
              if (orderRepairItems.length) {
                dispatch(
                  updateStatusRepairItemByServiceArea({
                    orderServiceId: orderService.id,
                    serviceAreaId: 3,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              dispatch(
                updateCurrentServiceAreaOrderService({
                  orderServiceId: orderService.id,
                  serviceAreaId: 3,
                })
              )

              dispatch(
                getAllOrderServiceByCurrentServiceArea({
                  serviceAreaId: 3,
                })
              )

              dispatch(
                updateStatusCarQueueByOrderService({
                  orderServiceId: orderService.id,
                  status: EnumStatusOrderService.STATUS_DOING.statusValue,
                })
              )
              showToastMessageSuccess()
              debounce(navigateUndercarriage, 2000)
            }
            if (serviceArea.id === 4) {
              dispatch(
                updateCurrentCapacityServiceArea({
                  ...serviceArea,
                  currentCapacity: +serviceArea.currentCapacity + 1,
                })
              )

              if (orderMaintenance) {
                dispatch(
                  updateStatusOrderMaintenanceByOrderServiceId({
                    orderServiceId: orderService.id,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              if (orderMaintenanceItems.length) {
                dispatch(
                  updateStatusMaintenanceItemByServiceArea({
                    orderMaintenanceId: orderMaintenance.id,
                    serviceAreaId: 4,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }
              if (orderRepairItems.length) {
                dispatch(
                  updateStatusRepairItemByServiceArea({
                    orderServiceId: orderService.id,
                    serviceAreaId: 4,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              dispatch(
                updateCurrentServiceAreaOrderService({
                  orderServiceId: orderService.id,
                  serviceAreaId: 4,
                })
              )

              dispatch(
                getAllOrderServiceByCurrentServiceArea({
                  serviceAreaId: 4,
                })
              )

              dispatch(
                updateStatusCarQueueByOrderService({
                  orderServiceId: orderService.id,
                  status: EnumStatusOrderService.STATUS_DOING.statusValue,
                })
              )
              showToastMessageSuccess()
              debounce(navigateRefrigeration, 2000)
            }
            if (serviceArea.id === 5) {
              dispatch(
                updateCurrentCapacityServiceArea({
                  ...serviceArea,
                  currentCapacity: +serviceArea.currentCapacity + 1,
                })
              )

              if (orderMaintenance) {
                dispatch(
                  updateStatusOrderMaintenanceByOrderServiceId({
                    orderServiceId: orderService.id,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              if (orderMaintenanceItems.length) {
                dispatch(
                  updateStatusMaintenanceItemByServiceArea({
                    orderMaintenanceId: orderMaintenance.id,
                    serviceAreaId: 5,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }
              if (orderRepairItems.length) {
                dispatch(
                  updateStatusRepairItemByServiceArea({
                    orderServiceId: orderService.id,
                    serviceAreaId: 5,
                    status: EnumStatusOrderService.STATUS_DOING.statusValue,
                  })
                )
              }

              dispatch(
                updateCurrentServiceAreaOrderService({
                  orderServiceId: orderService.id,
                  serviceAreaId: 5,
                })
              )

              dispatch(
                getAllOrderServiceByCurrentServiceArea({
                  serviceAreaId: 5,
                })
              )

              dispatch(
                updateStatusCarQueueByOrderService({
                  orderServiceId: orderService.id,
                  status: EnumStatusOrderService.STATUS_DOING.statusValue,
                })
              )
              showToastMessageSuccess()
              debounce(navigateWash, 2000)
            }
          }
        } else {
          showToastFailMessageByJobIsFalse()
          setLoadingButton(false)
        }
      }
    }
  }

  const showToastMessageSuccess = () => {
    toast.success('Điều xe vào khu vực thành công!', {
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
    toast.error('Xe hiện tại không có hạng mục sử dụng khu vực này!', {
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
  const showToastFailMessageByFull = () => {
    toast.error('Khu vực hiện tại đã đầy!', {
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
  const showToastFailMessageCurrentDifferentServiceArea = (errorMessage) => {
    toast.error(`${errorMessage}`, {
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

  const showToastFailMessageCurrentServiceArea = (serviceAreaName) => {
    toast.error(
      `Vui lòng hoàn thành các hạng mục của ${serviceAreaName} trước khi qua khu vực mới!`,
      {
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
      }
    )
  }

  useEffect(() => {
    handleGetAllServiceArea()
  }, [])

  return (
    serviceAreas &&
    serviceAreas.map((item, index) => {
      return (
        <Grid item md={2.4} key={index}>
          {loading && <Loading />}
          <Card>
            <ToastContainer autoClose={true} />
            <CardContent>
              <Typography variant="h5" component="div">
                {item.name}
              </Typography>
              <Typography
                sx={{ mb: 1.5 }}
                color="text.secondary"
                display={'flex'}
                justifyContent={'space-between'}
              >
                <Typography>Sức chứa:</Typography>
                <Typography>
                  {item.currentCapacity} / {item.capacity}
                </Typography>
              </Typography>
              <Typography
                variant="body2"
                display={'flex'}
                justifyContent={'space-between'}
              >
                <Typography>Trạng thái:</Typography>
                <Typography> {item.status}</Typography>
              </Typography>
            </CardContent>
            <CardActions>
              <LoadingButton
                size="small"
                variant="contained"
                type="button"
                color="secondary"
                loading={loadingButton}
                loadingPosition="start"
                onClick={() => {
                  handleImportCarToServiceArea(item, index)
                }}
                sx={{ ml: '10px', minWidth: '120px', minHeight: '35px' }}
              >
                Điều xe
              </LoadingButton>
            </CardActions>
          </Card>
        </Grid>
      )
    })
  )
}

export default ListServiceArea

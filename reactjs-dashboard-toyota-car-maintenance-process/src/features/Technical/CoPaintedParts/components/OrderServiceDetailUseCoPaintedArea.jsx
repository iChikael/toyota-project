import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { Box, Button, Checkbox, Grid } from '@mui/material'
import { useTheme } from '@mui/material'
import { DataGrid } from '@mui/x-data-grid'
import { tokens } from 'theme'
import Header from 'components/Header'
import {
  getAllOrderMaintenanceItemsByCurrentServiceArea,
  getAllOrderRepairItemsByCurrentServiceArea,
  getAccessoriesByOrderServiceItemId,
  updateStatusMaintenanceItemByServiceAreaDone,
  updateStatusRepairItemByServiceAreaDone,
  updateCurrentServiceAreaOrderService,
  getAllOrderServiceByCurrentServiceArea,
  getAllOrderMaintenanceItemsByOrderServiceId,
  getAllOrderRepairItemsByOrderServiceId,
  updateStatusCarQueueByOrderService,
  updateStatusMaintenanceItemByServiceAreaDoneAll,
  updateStatusRepairItemByServiceAreaDoneAll,
} from 'app/technicalSlice'
import { EnumStatusOrderService } from 'constants/EStatusOrderService'
import ModalAccessoryOrderService from './ModalAccessoryOrderService'
import {
  getServiceAreaById,
  updateCurrentCapacityServiceArea,
} from 'app/serviceAreaSlice'
import { ToastContainer, toast } from 'react-toastify'
import { LoadingButton } from '@mui/lab'
import ToastUtils from 'utils/ToastUtils'

import { AUTH } from 'constants/global'

import socketApi from 'api/socketApi'

const socket = socketApi.connectServer()

const accessToken = localStorage.getItem(AUTH.ACCESS_TOKEN)


const OrderServiceDetailUseCoPaintedArea = () => {
  const theme = useTheme()
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const colors = tokens(theme.palette.mode)
  const params = useParams()
  const orderServiceId = +params.orderId
  const serviceAreaId = 1
  const goBack = () => {
    navigate(-1)
  }

  const [hasData, setHasData] = useState(false)
  const [serviceArea, setServiceArea] = useState({})

  const viewOrderMaintenanceItemsByCurrentServiceArea = useSelector(
    (state) =>
      state.technical.data.viewOrderMaintenanceItemsByCurrentServiceArea
  )
  const viewOrderRepairItemsByCurrentServiceArea = useSelector(
    (state) => state.technical.data.viewOrderRepairItemsByCurrentServiceArea
  )

  const orderMaintenanceItemsByCurrentServiceArea = useSelector(
    (state) => state.technical.data.orderMaintenanceItemsByCurrentServiceArea
  )

  const orderRepairItemsByCurrentServiceArea = useSelector(
    (state) => state.technical.data.orderRepairItemsByCurrentServiceArea
  )

  const orderMaintenanceItems = useSelector(
    (state) => state.technical.data.orderMaintenanceItemsByOrderServiceId
  )

  const orderRepairItems = useSelector(
    (state) => state.technical.data.orderRepairItemsByOrderServiceId
  )

  const handleGetServiceItemsUseGeneralRepairArea = () => {
    const actionMaintenanceItem =
      getAllOrderMaintenanceItemsByCurrentServiceArea({
        orderServiceId: orderServiceId,
        serviceAreaId: serviceAreaId,
      })
    dispatch(actionMaintenanceItem)
    const actionRepairItem = getAllOrderRepairItemsByCurrentServiceArea({
      orderServiceId: orderServiceId,
      serviceAreaId: serviceAreaId,
    })
    dispatch(actionRepairItem)
  }

  const [openModalAccessory, setOpenModalAccessory] = useState(false)
  const handleOpenModalAccessory = (id) => {
    if (orderMaintenanceItemsByCurrentServiceArea.length) {
      orderMaintenanceItemsByCurrentServiceArea.map((item) => {
        if (id === 'BDĐK-' + item.id) {
          dispatch(
            getAccessoriesByOrderServiceItemId({
              id: item.id,
            })
          )
          setHasData(true)
          setOpenModalAccessory(true)
        }
      })
    }
    if (orderRepairItemsByCurrentServiceArea.length) {
      orderRepairItemsByCurrentServiceArea.map((item) => {
        if (id === 'GMGĐ-' + item.id) {
          dispatch(
            getAccessoriesByOrderServiceItemId({
              id: item.id,
            })
          )
          setHasData(true)
          setOpenModalAccessory(true)
        }
      })
    }
  }
  const handleCloseModalAccessory = () => {
    setOpenModalAccessory(false)
    setHasData(false)
  }

  const handleChangeProgress = (e, serviceItems) => {
    if (
      serviceItems.id.slice(0, 4) === 'BDĐK' &&
      serviceItems.status === EnumStatusOrderService.STATUS_DOING.statusValue
    ) {
      const action = updateStatusMaintenanceItemByServiceAreaDone({
        orderServiceId: orderServiceId,
        serviceAreaId: serviceAreaId,
        maintenanceItemId: serviceItems.id.slice(5),
        status: EnumStatusOrderService.STATUS_DONE.statusValue,
      })
      dispatch(action)
    }
    if (
      serviceItems.id.slice(0, 4) === 'BDĐK' &&
      serviceItems.status === EnumStatusOrderService.STATUS_DONE.statusValue
    ) {
      const action = updateStatusMaintenanceItemByServiceAreaDone({
        orderServiceId: orderServiceId,
        serviceAreaId: serviceAreaId,
        maintenanceItemId: serviceItems.id.slice(5),
        status: EnumStatusOrderService.STATUS_DOING.statusValue,
      })
      dispatch(action)
    }

    if (
      serviceItems.id.slice(0, 4) === 'GMGĐ' &&
      serviceItems.status === EnumStatusOrderService.STATUS_DOING.statusValue
    ) {
      const action = updateStatusRepairItemByServiceAreaDone({
        orderServiceId: orderServiceId,
        serviceAreaId: serviceAreaId,
        repairItemId: serviceItems.id.slice(5),
        status: EnumStatusOrderService.STATUS_DONE.statusValue,
      })
      dispatch(action)
    }
    if (
      serviceItems.id.slice(0, 4) === 'GMGĐ' &&
      serviceItems.status === EnumStatusOrderService.STATUS_DONE.statusValue
    ) {
      const action = updateStatusRepairItemByServiceAreaDone({
        orderServiceId: orderServiceId,
        serviceAreaId: serviceAreaId,
        repairItemId: serviceItems.id.slice(5),
        status: EnumStatusOrderService.STATUS_DOING.statusValue,
      })
      dispatch(action)
    }
  }

  const handleCompleteAllServiceItems = () => {
    dispatch(
      updateStatusMaintenanceItemByServiceAreaDoneAll({
        orderServiceId: orderServiceId,
        serviceAreaId: serviceAreaId,
        maintenanceItemId: '',
        status: EnumStatusOrderService.STATUS_DONE.statusValue,
      })
    )

    dispatch(
      updateStatusRepairItemByServiceAreaDoneAll({
        orderServiceId: orderServiceId,
        serviceAreaId: serviceAreaId,
        repairItemId: '',
        status: EnumStatusOrderService.STATUS_DONE.statusValue,
      })
    )
  }

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const navigateDone = () => {
    navigate('/technical/service-areas/co-painted-area')
  }

  const [loadingButton, setLoadingButton] = useState(false)

  const handleSubmit = () => {
    setLoadingButton(true)
    let count = 0
    if (viewOrderMaintenanceItemsByCurrentServiceArea.length) {
      viewOrderMaintenanceItemsByCurrentServiceArea.map((item) => {
        if (item.status === EnumStatusOrderService.STATUS_DONE.statusValue) {
          count += 1
        }
      })
    }
    if (viewOrderRepairItemsByCurrentServiceArea.length) {
      viewOrderRepairItemsByCurrentServiceArea.map((item) => {
        if (item.status === EnumStatusOrderService.STATUS_DONE.statusValue) {
          count += 1
        }
      })
    }
    if (
      count ===
      viewOrderMaintenanceItemsByCurrentServiceArea.length +
        viewOrderRepairItemsByCurrentServiceArea.length
    ) {
      const actionUpdateCurrentServiceAreaOrderService =
        updateCurrentServiceAreaOrderService({
          orderServiceId: orderServiceId,
          serviceAreaId: 0,
        })
      dispatch(actionUpdateCurrentServiceAreaOrderService)
      const actionUpdateCurrentCapacity = updateCurrentCapacityServiceArea({
        ...serviceArea,
        currentCapacity: +serviceArea.currentCapacity - 1,
      })
      dispatch(actionUpdateCurrentCapacity)

      const action = getAllOrderServiceByCurrentServiceArea({
        serviceAreaId: serviceAreaId,
      })
      dispatch(action)

      dispatch(
        getAllOrderMaintenanceItemsByOrderServiceId({ id: orderServiceId })
      )

      dispatch(
        getAllOrderRepairItemsByOrderServiceId({
          id: orderServiceId,
        })
      )

      let countDone = 0

      if (orderMaintenanceItems.length) {
        orderMaintenanceItems.map((item) => {
          if (item.status === EnumStatusOrderService.STATUS_DONE.statusValue) {
            countDone += 1
          }
        })
      }

      if (orderRepairItems.length) {
        orderRepairItems.map((item) => {
          if (item.status === EnumStatusOrderService.STATUS_DONE.statusValue) {
            countDone += 1
          }
        })
      }

      if (
        countDone +
          orderMaintenanceItemsByCurrentServiceArea.length +
          orderRepairItemsByCurrentServiceArea.length ===
        orderMaintenanceItems.length + orderRepairItems.length
      ) {
        const actionUpdateStatusCarQueue = updateStatusCarQueueByOrderService({
          orderServiceId: orderServiceId,
          status: EnumStatusOrderService.STATUS_DONE.statusValue,
        })
        dispatch(actionUpdateStatusCarQueue)
        .unwrap()
        .then(() => {
          socket.emit('get-all-order-service-done', accessToken)
        })
      }

      ToastUtils.showToastSuccessMessage(
        'Công việc đã hoàn thành. Gửi thành công!'
      )
      debounce(navigateDone, 2000)
    } else {
      ToastUtils.showToastFailMessage(
        'Vui lòng hoàn thành tất cả các hạng mục!'
      )
      setLoadingButton(false)
    }
  }

  const handleGetServiceArea = () => {
    const action = getServiceAreaById({ id: serviceAreaId })
    dispatch(action)
      .unwrap()
      .then((data) => {
        setServiceArea(data)
      })
      .catch((error) => {
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu chi tiết đơn hàng, vui lòng kiểm tra mạng'
          )
        }
      })
  }
  useEffect(() => {
    handleGetServiceItemsUseGeneralRepairArea()
    handleGetServiceArea()
  }, [])

  const columns = [
    { field: 'id', headerName: 'ID', headerAlign: 'center', align: 'center' },

    {
      field: 'name',
      headerName: 'Hạng mục dịch vụ',
      headerAlign: 'center',
      align: 'left',
      width: 550,
    },
    {
      field: 'job',
      headerName: 'Công việc cần làm',
      headerAlign: 'center',
      align: 'center',
      cellClassName: 'name-column--cell',
      width: 180,
    },
    {
      field: 'actions',
      headerName: 'Tiến độ',
      width: 180,
      headerAlign: 'center',
      align: 'center',
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
            <Checkbox
              checked={
                params.row.status ===
                EnumStatusOrderService.STATUS_DONE.statusValue
              }
              onChange={(e) => {
                handleChangeProgress(e, params.row)
              }}
              name={params.id}
            />
            Hoàn thành
          </Box>
        )
      },
    },
    {
      field: 'accessory',
      headerName: 'Chi tiết vật tư',
      width: 200,
      headerAlign: 'center',
      align: 'center',
      sortable: false,
      disableColumnMenu: true,

      renderCell: (params) => {
        const check = () => {
          let flag = true
          if (orderMaintenanceItemsByCurrentServiceArea.length) {
            orderMaintenanceItemsByCurrentServiceArea.forEach((e) => {
              if (e.orderMaintenanceItemAccessories.length) {
                if ('BDĐK-' + e.id === params.id) {
                  flag = false
                }
              }
            })
          }
          if (orderRepairItemsByCurrentServiceArea.length) {
            orderRepairItemsByCurrentServiceArea.forEach((e) => {
              if (e.orderRepairItemAccessories.length) {
                if ('GMGĐ-' + e.id === params.id) {
                  flag = false
                }
              }
            })
          }

          return flag
        }
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
              onClick={(e) => {
                handleOpenModalAccessory(params.id)
              }}
              disabled={check()}
            >
              Hiển thị
            </Button>
            {hasData && (
              <ModalAccessoryOrderService
                openModalAccessory={openModalAccessory}
                handleCloseModalAccessory={handleCloseModalAccessory}
              />
            )}
          </Box>
        )
      },
    },
  ]

  return (
    <Box m="20px" pb="20px">
      <ToastContainer autoClose={true} />
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Hạng mục dịch vụ khu vực đồng sơn" />
        <Button
          variant="contained"
          type="button"
          color="secondary"
          onClick={goBack}
          sx={{ mt: '10px', mb: '20px' }}
        >
          Danh sách lệnh sửa chữa khu vực đồng sơn
        </Button>
      </Grid>

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
        }}
      >
        <DataGrid
          // checkboxSelection
          rows={[
            ...viewOrderMaintenanceItemsByCurrentServiceArea,
            ...viewOrderRepairItemsByCurrentServiceArea,
          ]}
          columns={columns}
        />
      </Box>
      <br />
      <Box display="flex" justifyContent="end">
        <Button
          variant="contained"
          type="button"
          color="secondary"
          onClick={handleCompleteAllServiceItems}
          sx={{ ml: '15px' }}
        >
          Hoàn thành tất cả
        </Button>

        <LoadingButton
          variant="contained"
          type="button"
          color="secondary"
          onClick={handleSubmit}
          loading={loadingButton}
          loadingPosition="start"
          sx={{ ml: '10px', minWidth: '200px', minHeight: '35px' }}
        >
          Xác nhận hoàn thành
        </LoadingButton>
      </Box>
    </Box>
  )
}

export default OrderServiceDetailUseCoPaintedArea

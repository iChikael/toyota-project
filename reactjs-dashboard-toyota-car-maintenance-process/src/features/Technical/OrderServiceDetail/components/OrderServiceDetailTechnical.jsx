import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

import {
  Box,
  Button,
  Grid,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from '@mui/material'

import {
  getAllOrderMaintenanceItemsByOrderServiceId,
  getAllOrderMaintenancesByOrderServiceId,
  getAllOrderRepairItemsByOrderServiceId,
  getOrderServiceById,
} from 'app/technicalSlice'
import ModalMaintenanceItems from './ModalMaintenanceItems'
import ModalRepairItems from './ModalRepairItems'
import Loading from 'components/Loading'

const OrderServiceDetailTechnical = () => {
  const dispatch = useDispatch()
  const params = useParams()
  const orderServiceId = +params.orderId

  const orderService = useSelector((state) => state.technical.data.orderService)

  const orderMaintenance = useSelector(
    (state) => state.technical.data.orderMaintenanceByOrderServiceId
  )
  const orderRepairItemsByOrderServiceId = useSelector(
    (state) => state.technical.data.orderRepairItemsByOrderServiceId
  )

  const [loaded, setLoaded] = useState(true)
  const [loading, setLoading] = useState({
    getAllOrderMaintenancesByOrderServiceId: true,
    getOrderServiceById: true,
    getAllOrderMaintenanceItemsByOrderServiceId: true,
    getAllOrderRepairItemsByOrderServiceId: true,
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

  const handleGetOrderServiceById = () => {
    const action = getOrderServiceById({
      id: orderServiceId,
    })
    dispatch(action)
      .unwrap()
      .catch(() => {})
      .finally(() => {
        setLoading({ ...loading, getOrderServiceById: false })
      })
  }

  const handleGetAllOrderMaintenances = () => {
    const action = getAllOrderMaintenancesByOrderServiceId({
      id: orderServiceId,
    })
    dispatch(action)
      .unwrap()
      .catch(() => {})
      .finally(() => {
        setLoading({
          ...loading,
          getAllOrderMaintenancesByOrderServiceId: false,
        })
      })
  }

  const handleGetAllOrderMaintenanceItems = () => {
    const actionAllOrderMaintenanceItemsByOrderMaintenanceId =
      getAllOrderMaintenanceItemsByOrderServiceId({
        id: orderServiceId,
      })
    dispatch(actionAllOrderMaintenanceItemsByOrderMaintenanceId)
      .unwrap()
      .catch(() => {})
      .finally(() => {
        setLoading({
          ...loading,
          getAllOrderMaintenanceItemsByOrderServiceId: false,
        })
      })
  }
  const handleGetAllOrderRepairItems = () => {
    const action = getAllOrderRepairItemsByOrderServiceId({
      id: orderServiceId,
    })
    dispatch(action)
      .unwrap()
      .catch(() => {})
      .finally(() => {
        setLoading({
          ...loading,
          getAllOrderRepairItemsByOrderServiceId: false,
        })
      })
  }

  const [openModalRepairItems, setOpenModalRepairItems] = useState(false)
  const handleOpenModalRepairItems = () => setOpenModalRepairItems(true)
  const handleCloseModalRepairItems = () => setOpenModalRepairItems(false)

  const [openModalMaintenanceItems, setOpenModalMaintenanceItems] =
    useState(false)
  const handleOpenModalMaintenanceItems = () =>
    setOpenModalMaintenanceItems(true)
  const handleCloseModalMaintenanceItems = () =>
    setOpenModalMaintenanceItems(false)

  useEffect(() => {
    checkLoading()
  }, [JSON.stringify(loading)])

  useEffect(() => {
    handleGetOrderServiceById()

    if (orderMaintenance !== null) {
      handleGetAllOrderMaintenances()
      handleGetAllOrderMaintenanceItems()
    }

    handleGetAllOrderRepairItems()
  }, [orderServiceId])

  return (
    <Box>
      {loaded && <Loading />}
      <Grid container item md={11} mx={5} border={1}>
        <Table sx={{ minWidth: 650 }} aria-label="simple txable" border={1}>
          <TableHead>
            <TableRow>
              <TableCell component="th" align="center">
                GÓI DỊCH VỤ
              </TableCell>

              <TableCell component="th" align="center">
                TRẠNG THÁI
              </TableCell>
              <TableCell component="th" align="center">
                KHU VỰC HIỆN TẠI
              </TableCell>
              <TableCell component="th" align="center">
                CHI TIẾT DỊCH VỤ
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orderMaintenance && (
              <TableRow>
                <TableCell>{orderMaintenance.name}</TableCell>
                <TableCell align="center">{orderMaintenance.status}</TableCell>
                <TableCell align="center">
                  {(orderService && orderService.currentServiceArea) ||
                    'Khu vực xe chờ'}
                </TableCell>
                <TableCell align="center">
                  <Button
                    variant="contained"
                    type="button"
                    color="secondary"
                    onClick={handleOpenModalMaintenanceItems}
                  >
                    Hiển thị
                  </Button>
                </TableCell>
              </TableRow>
            )}
            {orderRepairItemsByOrderServiceId.length > 0 && (
              <TableRow>
                <TableCell>SỬA CHỮA</TableCell>
                <TableCell align="center">
                  {orderRepairItemsByOrderServiceId[0].status}
                </TableCell>
                <TableCell align="center">
                  {(orderService && orderService.currentServiceArea) ||
                    'Khu vực xe chờ'}
                </TableCell>
                <TableCell align="center">
                  <Button
                    variant="contained"
                    type="button"
                    color="secondary"
                    onClick={handleOpenModalRepairItems}
                  >
                    Hiển thị
                  </Button>
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </Grid>
      <ModalMaintenanceItems
        openModalMaintenanceItems={openModalMaintenanceItems}
        handleCloseModalMaintenanceItems={handleCloseModalMaintenanceItems}
      />
      <ModalRepairItems
        openModalRepairItems={openModalRepairItems}
        handleCloseModalRepairItems={handleCloseModalRepairItems}
      />
    </Box>
  )
}

export default OrderServiceDetailTechnical

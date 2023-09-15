import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'

import { Box, useTheme, MenuItem, Button } from '@mui/material'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'
import Iconify from 'components/Iconify'

import Header from 'components/Header'
import Swal from 'sweetalert2'

import {
  getAllCarQueue,
  getCarQueueById,
  deleteCarQueueById,
  setCarQueues,
} from 'app/carQueueSlice'
import ModalUpdateCarQueue from '../UpdateModal'

import Helper from 'utils/Helper'
import { getAllOrderServices } from 'app/technicalSlice'
import { getManagementCarPlateByCarPlate } from 'app/receptionSlice'
import Loading from 'components/Loading'
import ToastUtils from 'utils/ToastUtils'

const ListCarQueuePage = () => {
  const dispatch = useDispatch()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const navigate = useNavigate()

  const carQueues = useSelector((state) => state.carQueue.data.carQueues)

  const [loading, setLoading] = useState(true)

  const getCarQueues = () => {
    dispatch(getAllCarQueue())
      .unwrap()
      .then((dataCarQueues) => {
        if (dataCarQueues?.length) {
          dispatch(getAllOrderServices())
            .unwrap()
            .then((data) => {
              if (data?.length) {
                data.map((item) => {
                  dataCarQueues.map((carqueue, index) => {
                    if (item.carQueue.id === carqueue.id) {
                      const newObject = { ...carqueue }
                      const temp = JSON.parse(JSON.stringify(newObject))
                      temp.statusCreatedOrder = true
                      dispatch(setCarQueues({ index, newObject: temp }))
                    }
                  })
                })
              }
            })
        }
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách xe, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  const [modalIsOpen, setModalIsOpen] = useState(false)

  const openModal = (params) => {
    let count = 0
    dispatch(getAllOrderServices())
      .unwrap()
      .then((data) => {
        data.map((item) => {
          if (item.carQueue.id === params.id) {
            count += 1
          }
        })
        if (count === 0) {
          dispatch(getCarQueueById(params.id))
            .unwrap()
            .then(() => {
              setModalIsOpen(true)
            })
        } else {
          ToastUtils.showToastFailMessage(
            'Xe đã tạo lệnh sửa chữa, không thể cập nhật! '
          )
        }
      })
  }

  function closeModal() {
    setModalIsOpen(false)
  }

  let timeout
  function debounce(fn, delay) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn()
    }, delay)
  }

  const handleClickDeleteCarQueue = (params) => {
    let count = 0
    dispatch(getAllOrderServices())
      .unwrap()
      .then(async (data) => {
        data.map((item) => {
          if (item.carQueue.id === params.id) {
            count += 1
          }
        })
        if (count === 0) {
          const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
              title: 'titleDeleteItem',
            },
          })
          const result = await swalWithBootstrapButtons.fire({
            title: `Chắc chắn muốn xóa "${params.row.carNumberPlates}" khỏi hàng chờ`,
            showCancelButton: true,
            confirmButtonText: 'Chấp nhận',
            cancelButtonText: `Hủy bỏ`,
          })

          if (result.isConfirmed) {
            dispatch(deleteCarQueueById(params.id))
              .unwrap()
              .then(() => {
                ToastUtils.showToastSuccessMessage(
                  'Xóa xe khỏi hàng chờ thành công!'
                )
                debounce(getCarQueues, 2000)
              })
              .catch(() => {
                ToastUtils.showToastFailMessage(
                  'Xóa xe khỏi hàng chờ thất bại. Vui lòng kiểm tra lại thông tin! '
                )
              })
          }
        } else {
          ToastUtils.showToastFailMessage(
            'Xe đã tạo lệnh sửa chữa, không thể cập nhật! '
          )
        }
      })
  }

  const handleCreateOrderByCarQueue = (params) => {
    dispatch(getCarQueueById(params.id))
    dispatch(
      getManagementCarPlateByCarPlate({
        carPlate: params.row.carNumberPlates,
      })
    )
    navigate('/reception/order-services/create')
  }

  useEffect(() => {
    getCarQueues()
    closeModal()
  }, [])

  const columns = [
    { field: 'id', headerName: 'ID', headerAlign: 'center', align: 'center' },
    {
      field: 'carNumberPlates',
      headerName: 'Biển số xe',
      valueGetter: ({ row }) =>
        Helper.formatCarNumberPlate(row.carNumberPlates),
      headerAlign: 'center',
      align: 'center',
      width: 180,
    },
    {
      field: 'fullName',
      headerName: 'Tên khách hàng',
      headerAlign: 'center',
      align: 'left',
      width: 280,
    },
    {
      field: 'phone',
      headerName: 'Số điện thoại liên hệ',
      valueGetter: ({ row }) => Helper.formatPhoneNumber(row.phone),
      headerAlign: 'center',
      align: 'center',
      width: 150,
    },
    {
      field: 'status',
      headerName: 'Trạng thái',
      headerAlign: 'center',
      align: 'center',
      width: 180,
    },
    {
      field: 'actions',
      headerName: 'Cập nhật, xóa',
      headerAlign: 'center',
      align: 'center',
      width: 120,
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
            <MenuItem
              onClick={() => openModal(params)}
              disabled={params.row?.statusCreatedOrder || false}
            >
              <Iconify icon={'eva:edit-fill'} />
            </MenuItem>
            <MenuItem
              onClick={() => handleClickDeleteCarQueue(params)}
              disabled={params.row?.statusCreatedOrder || false}
            >
              <Iconify icon={'eva:trash-2-outline'} />
            </MenuItem>
          </Box>
        )
      },
    },
    {
      field: 'orders',
      headerName: 'Lệnh sửa chữa',
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
              disabled={params.row?.statusCreatedOrder || false}
              onClick={() => {
                handleCreateOrderByCarQueue(params)
              }}
              color="secondary"
              sx={{ ml: '10px' }}
            >
              Tạo lệnh sửa chữa
            </Button>
          </Box>
        )
      },
    },
  ]

  return (
    <Box m="20px" pb="20px">
      {loading && <Loading />}
      <ToastContainer autoClose={true} />
      <Header title="Danh sách xe hàng đợi" />
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
          rows={carQueues}
          columns={columns}
          components={{ Toolbar: GridToolbar }}
          initialState={{
            sorting: {
              sortModel: [{ field: 'id', sort: 'asc' }],
            },
          }}
        />
      </Box>
      <ModalUpdateCarQueue closeModal={closeModal} modalIsOpen={modalIsOpen} />
    </Box>
  )
}

export default ListCarQueuePage

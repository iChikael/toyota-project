import { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import { useParams } from 'react-router-dom'

import { Box, useTheme, MenuItem, CircularProgress } from '@mui/material'
import { DataGrid, GridToolbar, GridToolbarContainer } from '@mui/x-data-grid'

import Header from 'components/Header'
import { tokens } from 'theme'

import Swal from 'sweetalert2'
import Iconify from 'components/Iconify'

import ModalUpdateAccessory from './components/ModalUpdateAccessory'
import { ExportButton } from './components/exportExcel/ExportButton'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { ToastContainer } from 'react-toastify'
import Helper from 'utils/Helper'

import {
  getAllAccessories,
  deleteAccessoryById,
  getAccessoryById,
  getAllAccessoryRole,
} from 'app/accessoriesSlice'
import Loading from 'components/Loading'
import ToastUtils from 'utils/ToastUtils'

function CustomToolbar(props) {
  return (
    <GridToolbarContainer {...props}>
      <ExportButton />
    </GridToolbarContainer>
  )
}

const AccessoryListPage = () => {
  const dispatch = useDispatch()
  const [rows, setRows] = useState([])
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const [modalIsOpen, setModalIsOpen] = useState(false)
  const [selectedAccessoryId, setSelectedAccessoryId] = useState(null)

  const [loaded, setLoaded] = useState(true)
  const [loading, setLoading] = useState({
    getAllAccessories: true,
    getAllAccessoryRole: true,
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

  const getAccessories = () => {
    dispatch(getAllAccessories())
      .unwrap()
      .then((data) => {
        setRows(data)
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách phụ tùng, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading({ ...loading, getAllAccessories: false })
      })
  }
  const getAccessoryRole = () => {
    dispatch(getAllAccessoryRole())
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu loại phụ tùng, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading({ ...loading, getAllAccessoryRole: false })
      })
  }

  const openModal = async (index) => {
    if (!modalIsOpen) {
      try {
        const data = await new Promise((resolve, reject) => {
          dispatch(getAccessoryById(index))
            .unwrap()
            .then((data) => {
              resolve(data)
            })
            .catch((error) => {
              console.error('Error getting accessory by ID:', error)
              reject(error)
            })
        })
        console.log(data)
        setSelectedAccessoryId(index)
        setModalIsOpen(true)
        console.log(modalIsOpen)
      } catch (error) {
        console.error('Error opening modal:', error)
      }
    }
  }

  function closeModal() {
    setModalIsOpen(false)
  }

  const handleClickDeleteAccessory = async (index) => {
    try {
      const data = await new Promise((resolve, reject) => {
        dispatch(getAccessoryById(index))
          .unwrap()
          .then((data) => {
            resolve(data)
          })
          .catch((error) => {
            console.error('Error getting accessory by ID:', error)
            reject(error)
          })
      })

      const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
          title: 'titleDeleteItem',
        },
      })
      const result = await swalWithBootstrapButtons.fire({
        title: `Chắc chắn muốn xóa "${data.name}"`,
        showCancelButton: true,
        confirmButtonText: 'Chấp nhận',
        cancelButtonText: `Hủy bỏ`,
      })

      if (result.isConfirmed) {
        await new Promise((resolve, reject) => {
          dispatch(deleteAccessoryById(index))
            .unwrap()
            .then((result) => {
              if (deleteAccessoryById.fulfilled.match(result)) {
                console.log('Accessory deleted fail')
                reject(result)
              } else {
                ToastUtils.showToastSuccessMessage('Xóa linh kiện thành công!')
                setRows((prevRows) =>
                  prevRows.filter((accessory) => accessory.id !== index)
                )
                resolve()
              }
            })
            .catch((error) => {
              console.error('Error deleting accessory:', error)
              reject(error)
            })
        })
      }
    } catch (error) {
      console.error('Error handling delete accessory:', error)
    }
  }

  const columns = [
    {
      field: 'id',
      headerName: 'ID',
      headerAlign: 'center',
      align: 'center',
      width: 80,
    },
    {
      field: 'name',
      headerName: 'Tên sản phẩm',
      headerAlign: 'center',
      flex: 1,
    },
    {
      field: 'code',
      headerName: 'Mã sản phẩm',
      headerAlign: 'center',
      align: 'center',
      width: 150,
    },
    {
      headerName: 'Giá',
      valueGetter: ({ row }) => Helper.formatCurrencyToVND(row.price),
      headerAlign: 'center',
      align: 'right',
      width: 120,
    },
    {
      field: 'quantity',
      headerName: 'Số lượng',
      headerAlign: 'center',
      align: 'center',
      width: 100,
    },
    {
      field: 'unit',
      headerName: 'Đơn vị',
      headerAlign: 'center',
      align: 'center',
      width: 100,
    },
    {
      field: 'accessoryRole',
      headerName: 'Danh mục',
      headerAlign: 'center',
      align: 'center',
      width: 200,
    },
    {
      field: 'actions',
      headerName: 'Action',
      headerAlign: 'center',
      width: 100,
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
            <MenuItem onClick={() => openModal(parseInt(params.id))}>
              <Iconify icon={'eva:edit-fill'} />
            </MenuItem>
            <MenuItem onClick={() => handleClickDeleteAccessory(params.id)}>
              <Iconify icon={'eva:trash-2-outline'} />
            </MenuItem>
          </Box>
        )
      },
    },
  ]

  useEffect(() => {
    getAccessories()
    closeModal()
    getAccessoryRole()
  }, [])

  useEffect(() => {
    checkLoading()
  }, [JSON.stringify(loading)])

  return (
    <Box m="20px" pb="20px">
      <ToastContainer autoClose={true} />
      <Header title="Danh sách linh kiện - vật tư" />
      {loaded && <Loading />}
      <Box
        m="20px 0 0 0"
        height="75vh"
        sx={{
          '& .MuiDataGrid-root': {
            fontSize: '1rem',
            border: 'none',
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
          rows={rows}
          columns={columns}
          components={{ Toolbar: GridToolbar }}
          initialState={{
            sorting: {
              sortModel: [{ field: 'id', sort: 'asc' }],
            },
          }}
        />
      </Box>

      {selectedAccessoryId !== null && (
        <ModalUpdateAccessory
          closeModal={closeModal}
          modalIsOpen={modalIsOpen}
        />
      )}
    </Box>
  )
}

export default AccessoryListPage

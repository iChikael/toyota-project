import { useDispatch } from 'react-redux'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'

import { Box, Button, Grid, useTheme, MenuItem } from '@mui/material'

import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'
import Header from 'components/Header'
import {
  getAllRepairItem,
  getRepairItemById,
  deleteRepairItemById,
} from 'app/repairItemSlice'

import ModalUpdate from '../Update/ModalUpdate'
import Iconify from 'components/Iconify'
import { getAllServiceArea } from 'app/serviceAreaSlice'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import Swal from 'sweetalert2'
import Loading from 'components/Loading'
import ToastUtils from 'utils/ToastUtils'
import AddButton from 'components/Button/AddButton'

const RepairItemListPage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const [loading, setLoading] = useState(true)

  const handleGetAllRepairItems = () => {
    const getAllRepairItemAction = getAllRepairItem({})
    dispatch(getAllRepairItemAction)
      .unwrap()
      .then((data) => {
        setRows(data)
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách hạng mục sửa chữa, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  const [rows, setRows] = useState([])

  const handleCreateRepairItem = () => {
    navigate('/repair-items/create')
  }

  useEffect(() => {
    handleGetAllRepairItems()
    closeModal()
    getServiceArea()
  }, [])

  function closeModal() {
    setModalIsOpen(false)
  }

  const openModal = async (index) => {
    if (!modalIsOpen) {
      try {
        const data = await new Promise((resolve, reject) => {
          dispatch(getRepairItemById(index))
            .unwrap()
            .then((data) => {
              resolve(data)
            })
            .catch((error) => {
              console.error('Error getting repair by ID:', error)
            })
        })
        setSelectedRepairItemId(index)
        setModalIsOpen(true)
      } catch (error) {}
    }
  }

  const handleClickDeleteRepairItem = async (index) => {
    try {
      const data = await new Promise((resolve, reject) => {
        dispatch(getRepairItemById(index))
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
        title: `Chắc chắn muốn xóa "${data.title}"`,
        showCancelButton: true,
        confirmButtonText: 'Chấp nhận',
        cancelButtonText: `Hủy bỏ`,
      })

      if (result.isConfirmed) {
        await new Promise((resolve, reject) => {
          dispatch(deleteRepairItemById(index))
            .unwrap()
            .then((result) => {
              if (deleteRepairItemById.fulfilled.match(result)) {
                reject(result)
              } else {
                ToastUtils.showToastSuccessMessage('Xóa linh kiện thành công!')
                setRows((prevRows) =>
                  prevRows.filter((repairItem) => repairItem.id !== index)
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

  const getServiceArea = () => {
    dispatch(getAllServiceArea())
      .unwrap()
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách khu vực sửa chữa, vui lòng kiểm tra mạng'
          )
        }
      })
  }

  const theme = useTheme()

  const colors = tokens(theme.palette.mode)

  const [selectedRepairItemId, setSelectedRepairItemId] = useState(null)

  const [modalIsOpen, setModalIsOpen] = useState(false)

  const columns = [
    { field: 'id', headerName: 'ID', with: 50 },
    {
      field: 'title',
      headerName: 'Tên hạng mục sửa chữa',
      flex: 1,
      width: 450,
    },
    {
      field: 'serviceArea',
      headerName: 'Khu vực sửa chữa',
      valueGetter: ({ row }) => row.serviceArea.name,
      headerAlign: 'left',
      align: 'left',
      width: 250,
    },
    {
      field: 'actions',
      headerName: 'Actions',
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
            <MenuItem onClick={() => openModal(parseInt(params.id))}>
              <Iconify icon={'eva:edit-fill'} />
            </MenuItem>
            <MenuItem onClick={() => handleClickDeleteRepairItem(params.id)}>
              <Iconify icon={'eva:trash-2-outline'} />
            </MenuItem>
          </Box>
        )
      },
    },
  ]

  return (
    <Box m="20px" pb="20px">
      {loading && <Loading />}
      <ToastContainer autoClose={true} />
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Danh sách hạng mục sửa chữa" />
        <AddButton
          text=" Thêm hạng mục sửa chữa"
          onClick={handleCreateRepairItem}
        />
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
      {selectedRepairItemId !== null && (
        <ModalUpdate closeModal={closeModal} modalIsOpen={modalIsOpen} />
      )}
    </Box>
  )
}

export default RepairItemListPage

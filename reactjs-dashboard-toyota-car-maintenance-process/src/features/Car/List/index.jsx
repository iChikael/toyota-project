import { Box, Grid, useTheme } from '@mui/material'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'
import Header from 'components/Header'
import { getAllCars, deleteCarById, getCarById } from 'app/carSlice'
import { useDispatch } from 'react-redux'
import { useEffect, useState } from 'react'
import { IconButton } from '@mui/material'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import Swal from 'sweetalert2'

import { COUDINARY_URL } from 'constants/global'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import 'features/Accessory/List/components/customToast.css'
import { useNavigate } from 'react-router-dom'
import Loading from 'components/Loading'
import ToastUtils from 'utils/ToastUtils'
import AddButton from 'components/Button/AddButton'

const CarListPage = () => {
  const dispatch = useDispatch()
  const theme = useTheme()
  const navitage = useNavigate()
  const imageUrl = COUDINARY_URL + '/c_scale,w_80,h_60,q_100'

  const colors = tokens(theme.palette.mode)
  const [carById, setCarById] = useState({})

  const [loading, setLoading] = useState(true)

  const getCars = () => {
    const getAllCarAction = getAllCars({})
    dispatch(getAllCarAction)
      .unwrap()
      .then((data) => {
        setRows(data)
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

  const getCarByIdAction = (index) => {
    return new Promise((resolve, reject) => {
      dispatch(getCarById(index))
        .unwrap()
        .then((data) => {
          setCarById(data)
          resolve(data)
        })
        .catch((error) => {
          console.log('Getting car by id error: ', error)
          reject(error)
        })
    })
  }

  const handleDelete = (id) => {
    dispatch(deleteCarById(id))
      .unwrap()
      .then((result) => {
        if (deleteCarById.fulfilled.match(result)) {
          console.log('Car fail delete')
        } else {
          setRows((prevRows) => prevRows.filter((car) => car.id !== id))
        }
      })
  }

  const handleClickDeleteCar = async (index) => {
    try {
      const data = await getCarByIdAction(index)
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
        await handleDelete(index)
        ToastUtils.showToastSuccessMessage(`${data.title}! xóa thành công`)
      }
    } catch (error) {
      console.error('Error handling delete car:', error)
    }
  }

  const [rows, setRows] = useState([])

  const handleCreateCar = () => {
    navitage('/cars/create')
  }

  useEffect(() => {
    getCars()
  }, [])

  const columns = [
    { field: 'id', headerName: 'ID', headerAlign: 'center', align: 'center' },
    {
      field: 'title',
      headerName: 'Tên loại xe',
      headerAlign: 'center',
      align: 'left',
      width: 250,
    },
    {
      field: 'carAvatar.fileUrl',
      headerName: 'Ảnh mẫu',
      headerAlign: 'center',
      renderCell: (params) => (
        <img
          src={
            imageUrl +
            '/' +
            params.row.carAvatar.fileFolder +
            '/' +
            params.row.carAvatar.fileName
          }
        />
      ),
      align: 'center',
      width: 110,
    },
    {
      field: 'price',
      headerName: 'Giá bán',
      headerAlign: 'center',
      align: 'right',
      // flex: 1,
      width: 130,
    },
    {
      field: 'seatCode',
      headerName: 'Số chỗ ngồi',
      headerAlign: 'center',
      align: 'center',
      width: 100,
    },
    {
      field: 'fuelCode',
      headerName: 'Nhiên liệu',
      headerAlign: 'center',
      align: 'center',
      width: 150,
    },
    {
      field: 'originCode',
      headerName: 'Xuất xứ',
      headerAlign: 'center',
      align: 'center',
      width: 150,
    },
    {
      field: 'vehicle.modelCode',
      headerName: 'Dòng xe',
      headerAlign: 'center',
      align: 'center',
      valueGetter: ({ row }) => row.vehicle.modelCode,
      width: 100,
    },

    {
      field: 'actions',
      headerName: '',
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
            <IconButton onClick={() => alert(params.id)}>
              <EditIcon />
            </IconButton>
            <IconButton onClick={() => handleClickDeleteCar(params.id)}>
              <DeleteIcon />
            </IconButton>
          </Box>
        )
      },
    },
  ]

  return (
    <Box m="20px" pb="20px">
      {loading && <Loading />}
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Danh sách xe" />
        <AddButton text="Thêm mới xe" onClick={handleCreateCar} />
      </Grid>

      <ToastContainer autoClose={true} />
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
    </Box>
  )
}

export default CarListPage

import { Box, Button, Grid, useTheme, MenuItem } from '@mui/material'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import { tokens } from 'theme'
import Header from 'components/Header'
import { getAllServiceArea, getServiceAreaById } from 'app/serviceAreaSlice'
import { useDispatch } from 'react-redux'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Iconify from 'components/Iconify'
import ModalUpdateArea from './ModalUpdateArea'
import ToastUtils from 'utils/ToastUtils'

const ServiceAreaListPage = () => {
  const dispatch = useDispatch()

  const handleGetAllServiceArea = () => {
    const getgetServiceAreaAction = getAllServiceArea({})
    dispatch(getgetServiceAreaAction)
      .unwrap()
      .then((data) => {
        setRows(data)
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách khu vực sửa chữa, vui lòng kiểm tra mạng'
          )
        }
      })
  }

  const navitage = useNavigate()

  const handleCreateArea = () => {
    navitage('/service-area/create')
  }

  function closeModal() {
    setModalIsOpen(false)
  }

  const openModal = async (index) => {
    console.log(modalIsOpen)
    if (!modalIsOpen) {
      try {
        const data = await new Promise((resolve, reject) => {
          dispatch(getServiceAreaById({ id: index }))
            .unwrap()
            .then((data) => {
              resolve(data)
            })
            .catch((error) => {
              console.error('Error getting repair by ID:', error)
            })
        })
        setSelectedAreaId(index)
        setModalIsOpen(true)
      } catch (error) {}
    }
  }

  const [selectedAreaId, setSelectedAreaId] = useState()

  const [modalIsOpen, setModalIsOpen] = useState(false)
  const [rows, setRows] = useState([])

  useEffect(() => {
    handleGetAllServiceArea()
    closeModal()
  }, [])

  const theme = useTheme()

  const colors = tokens(theme.palette.mode)

  const columns = [
    { field: 'id', headerName: 'ID' },
    {
      field: 'name',
      headerName: 'Tên khu vực sửa chữa',
      flex: 1,
      width: 800,
    },
    {
      field: 'capacity',
      headerName: 'Sức chứa',
      headerAlign: 'left',
      align: 'left',
      width: 100,
    },
    {
      field: 'status',
      headerName: 'Trạng thái',
      headerAlign: 'left',
      align: 'left',
      width: 100,
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
            <MenuItem onClick={() => openModal(params.id)}>
              <Iconify icon={'eva:edit-fill'} />
            </MenuItem>
          </Box>
        )
      },
    },
  ]

  return (
    <Box m="20px" pb="20px">
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Danh sách khu vực sửa chữa" />
        {/* <Button
          variant="contained"
          type="button"
          color="secondary"
          onClick={handleCreateArea}
          sx={{ mt: '10px', mb: '20px' }}
        >
          Thêm mới khu vực
        </Button> */}
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
      {selectedAreaId !== null && (
        <ModalUpdateArea closeModal={closeModal} modalIsOpen={modalIsOpen} />
      )}
    </Box>
  )
}

export default ServiceAreaListPage

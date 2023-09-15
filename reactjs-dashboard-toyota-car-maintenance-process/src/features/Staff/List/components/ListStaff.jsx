import React, { useEffect, useState } from 'react'

import { Box, Typography, useTheme, Grid, Button } from '@mui/material'
import { DataGrid } from '@mui/x-data-grid'
import { tokens } from 'theme'

import Header from 'components/Header'
import AdminPanelSettingsOutlinedIcon from '@mui/icons-material/AdminPanelSettingsOutlined'
import LockOpenOutlinedIcon from '@mui/icons-material/LockOpenOutlined'
import SecurityOutlinedIcon from '@mui/icons-material/SecurityOutlined'
import { useDispatch } from 'react-redux'
import { getAllStaffs } from 'app/staffSlice'
import Helper from 'utils/Helper'
import Loading from 'components/Loading'
import { useNavigate } from 'react-router-dom'
import { COUDINARY_URL } from 'constants/global'
import AddButton from 'components/Button/AddButton'
import ToastUtils from 'utils/ToastUtils'
import { ToastContainer } from 'react-toastify'

const ListStaff = () => {
  const dispatch = useDispatch()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const navigate = useNavigate()

  const [staffs, setStaffs] = useState([])

  const [loading, setLoading] = useState(true)

  const getStaffs = () => {
    const getAllStaffsAction = getAllStaffs()
    dispatch(getAllStaffsAction)
      .unwrap()
      .then((data) => {
        setStaffs(data)
        console.log(data)
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách nhân viên, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  useEffect(() => {
    getStaffs()
  }, [])

  const handleCreateStaff = () => {
    navigate('/staffs/create')
  }

  const imageUrl = COUDINARY_URL + '/c_scale,w_50,h_50,q_100'

  const columns = [
    {
      field: 'avatar',
      headerName: 'Ảnh đại diện',
      headerAlign: 'center',
      renderCell: (params) => {
        return (
          <img
            src={
              params.row.staffAvatar?.fileFolder
                ? imageUrl +
                  '/' +
                  params.row.staffAvatar.fileFolder +
                  '/' +
                  params.row.staffAvatar.fileName
                : imageUrl +
                  '/' +
                  'toyota_car/staff_avatar' +
                  '/' +
                  '0750838d-a273-4448-9612-155c2190d271.png'
            }
            style={{
              borderRadius: '50%',
              objectFit: 'cover',
            }}
          />
        )
      },
      align: 'center',
      width: 110,
    },
    {
      field: 'fullName',
      headerName: 'Tên đầy đủ',
      headerAlign: 'center',
      flex: 1,
      cellClassName: 'name-column--cell',
    },
    {
      field: 'dob',
      headerName: 'Ngày sinh',
      headerAlign: 'center',
      align: 'center',
      valueGetter: ({ row }) => Helper.formatBirthday(row.dob),
      flex: 1,
    },
    {
      field: 'phone',
      headerName: 'Số điện thoại',
      headerAlign: 'center',
      align: 'center',
      flex: 1,
    },
    {
      field: 'email',
      headerName: 'Email',
      headerAlign: 'center',
      flex: 1,
    },
    {
      field: 'role',
      headerName: 'Chức vụ',
      headerAlign: 'center',
      flex: 1,
      renderCell: ({ row: { role } }) => {
        return (
          <Box
            width="60%"
            m="0 auto"
            p="5px"
            display="flex"
            justifyContent="center"
            backgroundColor={
              role === 'ADMIN'
                ? colors.greenAccent[600]
                : role === 'MANAGER'
                ? colors.greenAccent[700]
                : colors.greenAccent[700]
            }
            borderRadius="4px"
          >
            {role === 'ADMIN' && <AdminPanelSettingsOutlinedIcon />}
            {role === 'MANAGER' && <SecurityOutlinedIcon />}
            {role === 'CASHIER' && <LockOpenOutlinedIcon />}
            {role === 'RECEPTION' && <LockOpenOutlinedIcon />}
            {role === 'TECHNICAL' && <LockOpenOutlinedIcon />}
            {role === 'STOCKER' && <LockOpenOutlinedIcon />}
            <Typography color={colors.grey[100]} sx={{ ml: '5px' }}>
              {role}
            </Typography>
          </Box>
        )
      },
    },
  ]

  return (
    <Box m="20px" pb="20px">
      {loading && <Loading />}
      <ToastContainer />
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Danh sách nhân viên" />
        <AddButton text="Thêm mới nhân viên" onClick={handleCreateStaff} />
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
          rows={staffs}
          columns={columns}
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

export default ListStaff

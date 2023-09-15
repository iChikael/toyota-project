import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'

import { Box, Grid, useTheme } from '@mui/material'
import { DataGrid } from '@mui/x-data-grid'
import { tokens } from 'theme'

import Header from 'components/Header'
import { getAllCustomers } from 'app/customerSlice'
import Helper from 'utils/Helper'
import Loading from 'components/Loading'
import { COUDINARY_URL } from 'constants/global'
import ToastUtils from 'utils/ToastUtils'
import { ToastContainer } from 'react-toastify'
import AddButton from 'components/Button/AddButton'
import { useNavigate } from 'react-router-dom'

const ListCustomer = () => {
  const dispatch = useDispatch()
  const navitage = useNavigate()
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const navigate = useNavigate()

  const [loading, setLoading] = useState(true)

  const [customers, setCustomers] = useState([])

  const getCustomers = () => {
    const getAllCustomersAction = getAllCustomers()
    dispatch(getAllCustomersAction)
      .unwrap()
      .then((data) => {
        setCustomers(data)
        console.log(data)
      })
      .catch((err) => {
        if (err.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu danh sách khách hàng, vui lòng kiểm tra mạng'
          )
        }
      })
      .finally(() => {
        setLoading(false)
      })
  }

  const handleShowCreateCustomerPage = () => {
    navitage('/customers/create')
  }

  useEffect(() => {
    getCustomers()
  }, [])

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
              params.row.customerAvatarResDTO?.fileFolder
                ? imageUrl +
                  '/' +
                  params.row.customerAvatarResDTO.fileFolder +
                  '/' +
                  params.row.customerAvatarResDTO.fileName
                : imageUrl +
                  '/' +
                  'toyota_car/customer_avatar' +
                  '/' +
                  '595254aa-5315-441a-915e-863ec84f5653.png'
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
      align: 'left',
      width: 250,
      cellClassName: 'name-column--cell',
    },
    {
      field: 'dob',
      headerName: 'Ngày sinh',
      valueGetter: ({ row }) => Helper.formatBirthday(row.dob),
      headerAlign: 'center',
      align: 'center',
      width: 225,
    },
    {
      valueGetter: ({ row }) => Helper.formatPhoneNumber(row.phone),
      headerName: 'Số điện thoại',
      headerAlign: 'center',
      align: 'center',
      width: 225,
    },
    {
      field: 'email',
      headerName: 'Email',
      headerAlign: 'center',
      align: 'center',
      width: 300,
    },
    // {
    //   field: 'role',
    //   headerName: 'Action',
    //   flex: 1,
    //   renderCell: ({ row: { role } }) => {
    //     return (
    //       <Box
    //         width="60%"
    //         m="0 auto"
    //         p="5px"
    //         display="flex"
    //         justifyContent="center"
    //         backgroundColor={
    //           role === 'ADMIN'
    //             ? colors.greenAccent[600]
    //             : role === 'MANAGER'
    //             ? colors.greenAccent[700]
    //             : colors.greenAccent[700]
    //         }
    //         borderRadius="4px"
    //       >
    //         {role === 'ADMIN' && <AdminPanelSettingsOutlinedIcon />}
    //         {role === 'MANAGER' && <SecurityOutlinedIcon />}
    //         {role === 'CASHIER' && <LockOpenOutlinedIcon />}
    //         {role === 'RECEPTION' && <LockOpenOutlinedIcon />}
    //         {role === 'TECHNICAL' && <LockOpenOutlinedIcon />}
    //         {role === 'STOCKER' && <LockOpenOutlinedIcon />}
    //         <Typography color={colors.grey[100]} sx={{ ml: '5px' }}>
    //           {role}
    //         </Typography>
    //       </Box>
    //     )
    //   },
    // },
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
        <Header title="Danh sách khách hàng" />
        <AddButton
          text="Thêm mới khách hàng"
          onClick={handleShowCreateCustomerPage}
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
          rows={customers}
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

export default ListCustomer

import { useState } from 'react'

// @mui
import { Button, Popover } from '@mui/material'
// ----------------------------------------------------------------------

import MenuItem from '@mui/material/MenuItem'
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined'

import { useNavigate } from 'react-router-dom'

// component
import Iconify from 'components/Iconify'
import { clearData } from 'app/authSlice'
import { useDispatch, useSelector } from 'react-redux'
import { AUTH } from 'constants/global'

MenuBar.propTypes = {}

export default function MenuBar() {
  const auth = useSelector((state) => state.auth.data)
  const fullName = auth.fullName

  const dispatch = useDispatch()

  const [open, setOpen] = useState(null)
  const navigate = useNavigate()

  const handleOpenMenu = (event) => {
    setOpen(event.currentTarget)
  }

  const handleCloseMenu = () => {
    setOpen(null)
  }

  const handleLogout = () => {
    localStorage.removeItem(AUTH.ACCESS_TOKEN)

    const action = clearData()
    dispatch(action)

    navigate('/login')
  }

  return (
    <>
      {Boolean(open) ? (
        <Button
          variant="contained"
          sx={{
            position: 'absolute',
            minHeight: '35px',
            maxHeight: '35px',
            minWidth: '35px',
            maxWidth: '35px',
            padding: 0,
            textAlign: 'center',
            mt: '3px',
            // ml: 2,
            right: '0px !important',
            borderRadius: '50%',
            backgroundColor: '#F9FAFB',
            color: '#7266ba',
            boxShadow: 'none',
            '&:hover': {
              backgroundColor: '#F9FAFB',
              color: '#7266ba',
            },
          }}
          onClick={handleOpenMenu}
        >
          {/* <Iconify icon={'mdi:menu-open'} width={20} height={50} /> */}
          <PersonOutlinedIcon />
        </Button>
      ) : (
        <Button
          variant="contained"
          sx={{
            position: 'absolute',
            minHeight: '35px',
            maxHeight: '35px',
            minWidth: '35px',
            maxWidth: '35px',
            padding: 0,
            textAlign: 'center',
            mt: '3px',
            // ml: 2,
            right: '0 !important',
            borderRadius: '50%',
            // backgroundColor: '#7266ba',
            color: 'white',
            boxShadow: 'none',
            '&:hover': {
              backgroundColor: '#F9FAFB',
              color: '#7266ba',
            },
          }}
          onClick={handleOpenMenu}
        >
          {/* <Iconify icon={'mdi:menu-open'} width={20} height={50} /> */}
          <PersonOutlinedIcon />
        </Button>
      )}

      <Popover
        open={Boolean(open)}
        anchorEl={open}
        onClose={handleCloseMenu}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
        PaperProps={{
          sx: {
            p: 1,
            width: 160,
            '& .MuiMenuItem-root': {
              px: 1,
              typography: 'body2',
              borderRadius: 0.75,
            },
          },
        }}
      >
        <MenuItem>
          <Iconify icon={'mdi:user-outline'} sx={{ mr: 2 }} />
          {fullName}
        </MenuItem>
        <MenuItem>
          <Iconify icon={'flat-color-icons:edit-image'} sx={{ mr: 2 }} />
          Sửa thông tin
        </MenuItem>

        <MenuItem sx={{ color: 'error.main' }} onClick={handleLogout}>
          <Iconify icon={'entypo:log-out'} sx={{ mr: 2 }} />
          Đăng xuất
        </MenuItem>
      </Popover>
    </>
  )
}

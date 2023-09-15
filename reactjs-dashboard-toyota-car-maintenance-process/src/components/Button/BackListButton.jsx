import React from 'react'
import { Button } from '@mui/material'

import ListIcon from '@mui/icons-material/List'

const BackListButton = ({ text, onClick }) => {
  return (
    <Button
      variant="contained"
      type="button"
      color="secondary"
      onClick={onClick}
      sx={{
        color: '#7266ba',
        backgroundColor: '#fff',
        border: '1px solid transparent',
        borderColor: '#7266ba',
        fontSize: '1rem',
        fontWeight: 600,
        ml: 1,
        mt: 1,
        '&:hover': {
          color: '#fff',
          backgroundColor: '#7266ba',
        },
      }}
    >
      <ListIcon sx={{ fontSize: '24px', mr: 1 }} />
      {text ? text : 'Quay về trang danh sách'}
    </Button>
  )
}

export default BackListButton

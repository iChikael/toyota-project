import React from 'react'

import AddIcon from '@mui/icons-material/Add'
import { Button } from '@mui/material'

const AddButton = ({ text, onClick }) => {
  return (
    <Button
      variant="contained"
      type="button"
      color="secondary"
      onClick={onClick}
      sx={{
        backgroundColor: 'green',
        color: '#fff',
        fontSize: '1rem',
        '&:hover': {
          backgroundColor: '#3cb03c',
        },
      }}
    >
      <AddIcon sx={{ fontSize: '24px' }} />
      {text ? text : 'Thêm mới'}
    </Button>
  )
}

export default AddButton

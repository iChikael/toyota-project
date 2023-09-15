import React, { useState } from 'react'
import { FormControl } from '@mui/material'
import { Button } from '@mui/material'
import { FormHelperText } from '@mui/material'
import CloudUploadIcon from '@mui/icons-material/CloudUpload'

const ImageInput = (props) => {
  const [fileName, setFileName] = useState('')

  const handleImageChange = (e) => {
    e.preventDefault()
    let reader = new FileReader()
    let file = e.target.files[0]
    if (file) {
      reader.onloadend = () => setFileName(file.name)
      reader.readAsDataURL(file)
      props.setFieldValue(props.field.name, file)
    }
    props.onChangeCustom && props.onChangeCustom(e)
  }

  const errorMessage = props.form.errors[props.field.name]

  return (
    <FormControl margin="normal">
      <input
        style={{ display: 'none' }}
        id="video-upload"
        name={props.field.name}
        type="file"
        accept="image/*"
        onChange={handleImageChange}
      />
      <label htmlFor="video-upload">
        <Button
          variant="contained"
          margin="normal"
          component="span"
          startIcon={<CloudUploadIcon />}
          sx={{
            backgroundColor: 'blue',
          }}
        >
          Upload a file
        </Button>
      </label>
      {fileName ? (
        <FormHelperText id="video-upload-filename">{fileName}</FormHelperText>
      ) : null}
      {errorMessage ? (
        <FormHelperText id="video-upload-helper-text" error={true}>
          {errorMessage}
        </FormHelperText>
      ) : null}
    </FormControl>
  )
}

export default ImageInput

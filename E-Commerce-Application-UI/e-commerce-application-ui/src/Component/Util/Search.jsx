import React from 'react'
import {InputBase , Box , styled} from '@mui/material'
import SearchIcon from "@mui/icons-material/Search";


const SearchContainer = styled(Box)`
  border-radius: 2px;
  margin-left: 10px;
  width: 38%;
  background-color: #fff;
  display: flex;
`;

const InputSearchBase =  styled(InputBase)`

`;
const SearchIconWrapper = styled(Box)`
color: blue;
`
const Search = () => {
  return (
   <SearchContainer>
     <InputBase sx={{ backgroundColor: '#fff' }} placeholder='Search For Products, Brand  and more'/>
     <SearchIconWrapper>
      <SearchIcon/>
     </SearchIconWrapper>
   </SearchContainer>
  
  )
}

export default Search
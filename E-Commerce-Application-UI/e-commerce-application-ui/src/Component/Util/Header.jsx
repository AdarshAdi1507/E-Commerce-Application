//  import {AppBar, Toolbar} from '@mui/material'
// const Header =()=>{
//     return(
//         <AppBar>
//             <Toolbar>

//             </Toolbar>
//         </AppBar>
//     )
// }
// export default Header
// import React from 'react'
// import { Link } from 'react-router-dom'
// import {AppBar, Toolbar, styled ,Box ,Typography} from '@mui/material'
// import Search from './Search'

// // Components used in this 

// const Header = () => {
//     //this below allows you to ovveride the css given by material UI
//     const StyledHeader = styled(AppBar)`
//     background-color : ;
//     height :54px;
//      `;

//      const Component = styled(Box)`
//      margin-left : 12%;
//      line-height:1;
//      `

//       const SubHeading = styled(Typography)`
//       font-size: 10px;
//       font-style :italic
//       `
//       const PlusImage = styled("img")({
//         width :10,
//         height: 10,
//         marginLeft :4
//       })

//      const logoURL = "https://static-assets-web.flixcart.com/www/linchpin/fk-cp-zion/img/flipkart-plus_8d85f4.png"
//      const subURl ="https://static-assets-web.flixcart.com/www/linchpin/fk-cp-zion/img/plus_aef861.png"
//   return (
//     <div className='flex item-center justify-around bg-blue-900 text-slate 100 py-2 text-xl border-b-2 border-slate-900'>
//     <StyledHeader>
//     {/* // this is appbar tag with renamed as styledheader */}
// <Toolbar>
// <Link to={"/"}>Logo</Link>

//         <Link to={"/login"}></Link>

//         <Link to={"/register"}>Register</Link>
//         <Component>
//         {/* this is a box tag with renamed as component */}
// <img src={logoURL} alt="logo" style={{width:75 }} />
// <Box style={{display : 'flex'}}>
// <SubHeading>
//     Explore&nbsp;{/*we can use typography instead of p tag in mui*/}
//     <Box component="span" style={{color: "#FFC500"}}> Plus</Box>
// </SubHeading>
// <PlusImage src={subURl} alt="sub-logo" />
// </Box>
//         </Component>
//         <Search/>

        
// </Toolbar>

//     </StyledHeader>
        
   
//     </div>
//   )
// }

// export default Header

// import React from 'react'
// import { Link } from 'react-router-dom'
// import { IoIosArrowDown } from "react-icons/io";
// import { CiSearch } from "react-icons/ci";
// import { BsBox } from "react-icons/bs";
// import { GoHeart } from "react-icons/go";

// const Header = () => {
//   return (
//     <div className='flex item-center justify-around text-slate-950 text-slate-100 py-2  border-b-2 border-slate-1 text-center text-lg'>
//     <Link to={"/"}><img src="https://static-assets-web.flixcart.com/batman-returns/batman-returns/p/images/fkheaderlogo_exploreplus-44005d.svg" width="160" height="40" title="Flipkart"/></Link>
//     <section className='flex items-center gap-2 bg-slate-200 w-[32rem] rounded-md'><CiSearch /><input className='w-full text-lg bg-slate-200' type="text" placeholder="Search for Products, Brands and More" value="" ></input></section>
//     <Link to={"/login"} className='relative group transition-all'>
//      <p className='flex items-center justify-around  w-24  rounded-md hover:bg-blue-700 hover:text-white'>
//       <CgProfile />Login <IoIosArrowDown className='rotate-180 transition-all hover:rotate-0'/>
//      </p>
//      <div className=' flex justify-around absolute top-10 hidden w-60 flex-col gap-1 rounded-md bg-white py=3  shadow-md transition-all group-hover:flex '>
//       <Link to={"/register"} className='flex item-center gap-5 '>  New customer? <p className='text-blue-600 '>Sing Up</p></Link>
//       <Link to={"#"} className='flex  gap-2'><CgProfile />My Profile</Link>
//       <Link to={"#"} className='flex gap-2 '><BsBox />Orders</Link>
//       <Link to={"/wishlist"} className='flex gap-2 '><GoHeart /> Wishlist</Link>
//      </div>
//     </Link>
//     <Link to={"/register"} className='flex items-center gap-2'><img src="https://static-assets-web.flixcart.com/batman-returns/batman-returns/p/images/Store-9eeae2.svg" alt="Become a Seller" class="_1XmrCc"/>Become a seller</Link>
//     </div>
//   )
// }
// export default Header
import React, { useState } from 'react';
import { Link } from 'react-router-dom'
import { CgProfile } from "react-icons/cg";
import { IoIosArrowDown } from "react-icons/io";
import { CiSearch } from "react-icons/ci";
import { BsBox } from "react-icons/bs";
import { GoHeart } from "react-icons/go";
import { FaBell } from 'react-icons/fa';
import { FaS } from 'react-icons/fa6';


const Header = () => {

  const [searchValue, setSearchValue] = useState('');

  return (
    <div className='flex item-center justify-around text-slate-950 text-slate-100 py-2  border-b-2 border-slate-1 text-center text-lg'>
        <Link to={"/"}><img src="https://static-assets-web.flixcart.com/batman-returns/batman-returns/p/images/fkheaderlogo_exploreplus-44005d.svg" width="160" height="40" title="Flipkart"/></Link>
        <section className='flex items-center gap-2 bg-slate-200 w-[32rem] rounded-md'><CiSearch /><input className='w-full text-lg bg-slate-200' type="text" placeholder="Search for Products, Brands and More" value="" ></input></section>
        <Link to={"/login"} className='relative group transition-all'>
          <p className='flex items-center justify-around  w-24  rounded-md hover:bg-blue-700 hover:text-white'>
           <CgProfile />Login <IoIosArrowDown className='rotate-180 transition-all hover:rotate-0'/>
          </p>
          <div className=' flex justify-around absolute top-10 hidden w-60 flex-col gap-1 rounded-md bg-white py=3  shadow-md transition-all group-hover:flex '>
          <Link to={"/register"} className='flex item-center gap-5 '>  New customer? <p className='text-blue-600 '>Sign Up</p></Link>
            <Link to={"#"} className='flex  gap-2'><CgProfile />My Profile</Link>
          <Link to={"#"} className='flex gap-2 '><BsBox />Orders</Link>
           <Link to={"/wishlist"} className='flex gap-2 '><GoHeart /> Wishlist</Link>
           <Link to={"/notifications"} className='flex gap-2'><FaBell />Notifications</Link>
          <Link to={"/logout"} className='flex gap-2'><FaS />Logout</Link>
          </div>
         </Link>
         <Link to={"/register"} className='flex items-center gap-2'><img src="https://static-assets-web.flixcart.com/batman-returns/batman-returns/p/images/Store-9eeae2.svg" alt="Become a Seller" class="_1XmrCc"/>Become a seller</Link>
         </div>
  );
};

export default Header;
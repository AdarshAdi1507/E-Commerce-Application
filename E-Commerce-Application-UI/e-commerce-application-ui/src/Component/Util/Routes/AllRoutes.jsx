import React from 'react'
import {Route , Routes} from 'react-router-dom'
import Home from '../../Public/Home';
import Cart from '../../Private/Customer/Cart';
import Wishlist from '../../Private/Customer/Wishlist';
import SellerDashboard from '../../Private/Seller/SellerDashboard';
import AddProduct from '../../Private/Seller/AddProduct';
import Explore from '../../Private/Customer/Explore';
import Register from '../../Public/Register';

import LoginPage from '../../Public/LoginPage';
import App from '../../../App';
import EditProfile from '../../Private/Common/EditProfile';

const AllRoutes = () => {

    const user = {
        userId: "123",
        userName:"abc",
        role:"CUSTOMER",
        authenticated :true,
        accessExpiration :3600,
        refreshExpiration: 129600
    }

    const {role , authenticated } = user;
    let routes =[];
 if(authenticated){
    (role==="SELLER")?
    routes.push(
        <Route path='/seller-dashboard' element ={<SellerDashboard/>}/>,
        <Route path ='/add-product' element ={<AddProduct/>}/>
    )
    :(role ==="CUSTOMER")&&
    routes.push(
        <Route path ='/explore' element={<Explore/>}/>,
        <Route path ='/cart' element={<Cart/>}/>,
        <Route path= '/wishlist' element={<Wishlist/>}/>
    )

    routes.push(
        <Route path='/explore' element={<Explore/>}/>,
        <Route path='/add-address' element={<Cart/>}/>,
        <Route path ='/editprofile' element ={<EditProfile/>}/>
        )
    
 }else {
    (role==="CUSTOMER")&&
    routes.push(
        <Route path='/explore' element={<Explore/>}/>,
        <Route path='/' element={<Home/>}/>,
        <Route path='/login' element={<LoginPage/>}/>,
        <Route path='/=register' element={<Register/>}/>
        
    )
 }
  return (
  <Routes>
<Route path='/' element={<App/>}>{routes}</Route>
  </Routes>
  )
}

export default AllRoutes
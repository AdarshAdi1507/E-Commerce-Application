// import './App.css'
// import Header from './Component/Util/Header'
// import {Outlet} from 'react-router-dom'

// function App() {
  

//   return (
// <div>
//   <Header/>
//   <Outlet/>
// </div>
//   )
// }

// export default App

import React from 'react'
import Headers from './Component/Util/Header'
import { Outlet } from 'react-router-dom'

const App = () => {
  return (
<div>
<Headers/>
</div>
  )
}

export default App

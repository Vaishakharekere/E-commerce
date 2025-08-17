// import { motion } from 'framer-motion'
// import { Users, Package, ShoppingCart, DollarSign, TrendingUp, Eye } from 'lucide-react'

// const AdminDashboard = () => {
//   // Mock dashboard data
//   const stats = [
//     { title: 'Total Users', value: '1,234', icon: Users, color: 'text-blue-600', bgColor: 'bg-blue-100' },
//     { title: 'Total Products', value: '567', icon: Package, color: 'text-green-600', bgColor: 'bg-green-100' },
//     { title: 'Total Orders', value: '89', icon: ShoppingCart, color: 'text-purple-600', bgColor: 'bg-purple-100' },
//     { title: 'Revenue', value: '$12,345', icon: DollarSign, color: 'text-orange-600', bgColor: 'bg-orange-100' }
//   ]

//   const recentOrders = [
//     { id: 'ORD-001', customer: 'john@example.com', amount: 299.99, status: 'Processing' },
//     { id: 'ORD-002', customer: 'sarah@example.com', amount: 149.99, status: 'Shipped' },
//     { id: 'ORD-003', customer: 'mike@example.com', amount: 89.99, status: 'Delivered' }
//   ]

//   const topProducts = [
//     { name: 'Wireless Headphones', sales: 45, revenue: 3595.55 },
//     { name: 'Smart Watch', sales: 32, revenue: 6396.68 },
//     { name: 'Camera Lens', sales: 18, revenue: 8999.82 }
//   ]

//   return (
//     <div className="min-h-screen bg-gray-50 py-8">
//       <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
//         <div className="mb-8">
//           <h1 className="text-3xl font-bold text-gray-900">Admin Dashboard</h1>
//           <p className="text-gray-600">Overview of your e-commerce store performance</p>
//         </div>

//         {/* Stats Grid */}
//         <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
//           {stats.map((stat, index) => {
//             const Icon = stat.icon
//             return (
//               <motion.div
//                 key={stat.title}
//                 initial={{ opacity: 0, y: 20 }}
//                 animate={{ opacity: 1, y: 0 }}
//                 transition={{ delay: index * 0.1 }}
//                 className="card"
//               >
//                 <div className="p-6">
//                   <div className="flex items-center">
//                     <div className={`p-3 rounded-full ${stat.bgColor}`}>
//                       <Icon className={`w-6 h-6 ${stat.color}`} />
//                     </div>
//                     <div className="ml-4">
//                       <p className="text-sm font-medium text-gray-600">{stat.title}</p>
//                       <p className="text-2xl font-bold text-gray-900">{stat.value}</p>
//                     </div>
//                   </div>
//                 </div>
//               </motion.div>
//             )
//           })}
//         </div>

//         <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
//           {/* Recent Orders */}
//           <motion.div
//             initial={{ opacity: 0, x: -20 }}
//             animate={{ opacity: 1, x: 0 }}
//             className="card"
//           >
//             <div className="p-6">
//               <div className="flex items-center justify-between mb-6">
//                 <h2 className="text-xl font-semibold text-gray-900">Recent Orders</h2>
//                 <button className="text-primary-600 hover:text-primary-700 text-sm font-medium">
//                   View All
//                 </button>
//               </div>
              
//               <div className="space-y-4">
//                 {recentOrders.map((order) => (
//                   <div key={order.id} className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
//                     <div>
//                       <p className="font-medium text-gray-900">{order.id}</p>
//                       <p className="text-sm text-gray-500">{order.customer}</p>
//                     </div>
//                     <div className="text-right">
//                       <p className="font-medium text-gray-900">${order.amount}</p>
//                       <span className={`inline-flex px-2 py-1 text-xs font-medium rounded-full ${
//                         order.status === 'Delivered' ? 'bg-green-100 text-green-800' :
//                         order.status === 'Shipped' ? 'bg-blue-100 text-blue-800' :
//                         'bg-yellow-100 text-yellow-800'
//                       }`}>
//                         {order.status}
//                       </span>
//                     </div>
//                   </div>
//                 ))}
//               </div>
//             </div>
//           </motion.div>

//           {/* Top Products */}
//           <motion.div
//             initial={{ opacity: 0, x: 20 }}
//             animate={{ opacity: 1, x: 0 }}
//             className="card"
//           >
//             <div className="p-6">
//               <div className="flex items-center justify-between mb-6">
//                 <h2 className="text-xl font-semibold text-gray-900">Top Products</h2>
//                 <button className="text-primary-600 hover:text-primary-700 text-sm font-medium">
//                   View All
//                 </button>
//               </div>
              
//               <div className="space-y-4">
//                 {topProducts.map((product, index) => (
//                   <div key={product.name} className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
//                     <div className="flex items-center">
//                       <span className="w-8 h-8 bg-primary-100 text-primary-600 rounded-full flex items-center justify-center text-sm font-medium mr-3">
//                         {index + 1}
//                       </span>
//                       <div>
//                         <p className="font-medium text-gray-900">{product.name}</p>
//                         <p className="text-sm text-gray-500">{product.sales} sales</p>
//                       </div>
//                     </div>
//                     <div className="text-right">
//                       <p className="font-medium text-gray-900">${product.revenue.toFixed(2)}</p>
//                       <p className="text-sm text-gray-500">Revenue</p>
//                     </div>
//                   </div>
//                 ))}
//               </div>
//             </div>
//           </motion.div>
//         </div>

//         {/* Quick Actions */}
//         <motion.div
//           initial={{ opacity: 0, y: 20 }}
//           animate={{ opacity: 1, y: 0 }}
//           transition={{ delay: 0.3 }}
//           className="card mt-8"
//         >
//           <div className="p-6">
//             <h2 className="text-xl font-semibold text-gray-900 mb-6">Quick Actions</h2>
            
//             <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
//               <button className="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors text-left">
//                 <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center mb-3">
//                   <Package className="w-5 h-5 text-blue-600" />
//                 </div>
//                 <h3 className="font-medium text-gray-900">Add Product</h3>
//                 <p className="text-sm text-gray-500">Create new product listing</p>
//               </button>

//               <button className="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors text-left">
//                 <div className="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center mb-3">
//                   <Users className="w-5 h-5 text-green-600" />
//                 </div>
//                 <h3 className="font-medium text-gray-900">Manage Users</h3>
//                 <p className="text-sm text-gray-500">View and edit user accounts</p>
//               </button>

//               <button className="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors text-left">
//                 <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center mb-3">
//                   <ShoppingCart className="w-5 h-5 text-purple-600" />
//                 </div>
//                 <h3 className="font-medium text-gray-900">View Orders</h3>
//                 <p className="text-sm text-gray-500">Process and track orders</p>
//               </button>

//               <button className="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors text-left">
//                 <div className="w-10 h-10 bg-orange-100 rounded-lg flex items-center justify-center mb-3">
//                   <TrendingUp className="w-5 h-5 text-orange-600" />
//                 </div>
//                 <h3 className="font-medium text-gray-900">Analytics</h3>
//                 <p className="text-sm text-gray-500">View detailed reports</p>
//               </button>
//             </div>
//           </div>
//         </motion.div>
//       </div>
//     </div>
//   )
// }

// export default AdminDashboard
import { useEffect, useState } from "react"
import { motion } from "framer-motion"
import { Users, Package, ShoppingCart, DollarSign, TrendingUp } from "lucide-react"
import axios from "axios"

const API_BASE = "http://localhost:8080/api"

const AdminDashboard = () => {
  const [stats, setStats] = useState([])
  const [recentOrders, setRecentOrders] = useState([])
  const [topProducts, setTopProducts] = useState([])

  // ✅ Load token (after Admin login)
  const token = localStorage.getItem("token")

  // Axios instance with auth header
  const axiosInstance = axios.create({
    baseURL: API_BASE,
    headers: {
      Authorization: `Bearer ${token}`
    }
  })

  useEffect(() => {
    // Fetch Dashboard Data
    const fetchDashboardData = async () => {
      try {
        // 🚀 You can create a backend route like: /api/admin/dashboard
        const statsRes = await axiosInstance.get("/admin/dashboard/stats")
        const ordersRes = await axiosInstance.get("/orders")
        const productsRes = await axiosInstance.get("/products")

        setStats(statsRes.data)         // Example: [{ title: 'Users', value: 123 }]
        setRecentOrders(ordersRes.data) // Example: [{ id, customer, amount, status }]
        setTopProducts(productsRes.data) // Example: [{ name, sales, revenue }]
      } catch (error) {
        console.error("Error fetching dashboard data", error)
      }
    }

    fetchDashboardData()
  }, [])

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900">Admin Dashboard</h1>
          <p className="text-gray-600">Overview of your e-commerce store performance</p>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          {stats.map((stat, index) => {
            const Icon = stat.icon === "users" ? Users 
                        : stat.icon === "products" ? Package
                        : stat.icon === "orders" ? ShoppingCart 
                        : DollarSign
            return (
              <motion.div
                key={stat.title}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.1 }}
                className="card"
              >
                <div className="p-6">
                  <div className="flex items-center">
                    <div className="p-3 rounded-full bg-gray-100">
                      <Icon className="w-6 h-6 text-gray-700" />
                    </div>
                    <div className="ml-4">
                      <p className="text-sm font-medium text-gray-600">{stat.title}</p>
                      <p className="text-2xl font-bold text-gray-900">{stat.value}</p>
                    </div>
                  </div>
                </div>
              </motion.div>
            )
          })}
        </div>

        {/* Recent Orders */}
        <motion.div initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="card">
          <div className="p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-6">Recent Orders</h2>
            <div className="space-y-4">
              {recentOrders.map((order) => (
                <div key={order.id} className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div>
                    <p className="font-medium text-gray-900">{order.id}</p>
                    <p className="text-sm text-gray-500">{order.customer}</p>
                  </div>
                  <div className="text-right">
                    <p className="font-medium text-gray-900">${order.amount}</p>
                    <span className={`inline-flex px-2 py-1 text-xs font-medium rounded-full ${
                      order.status === "Delivered" ? "bg-green-100 text-green-800" :
                      order.status === "Shipped" ? "bg-blue-100 text-blue-800" :
                      "bg-yellow-100 text-yellow-800"
                    }`}>
                      {order.status}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </motion.div>

        {/* Top Products */}
        <motion.div initial={{ opacity: 0, x: 20 }} animate={{ opacity: 1, x: 0 }} className="card mt-8">
          <div className="p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-6">Top Products</h2>
            <div className="space-y-4">
              {topProducts.map((product, index) => (
                <div key={product.name} className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div className="flex items-center">
                    <span className="w-8 h-8 bg-primary-100 text-primary-600 rounded-full flex items-center justify-center text-sm font-medium mr-3">
                      {index + 1}
                    </span>
                    <div>
                      <p className="font-medium text-gray-900">{product.name}</p>
                      <p className="text-sm text-gray-500">{product.sales} sales</p>
                    </div>
                  </div>
                  <div className="text-right">
                    <p className="font-medium text-gray-900">${product.revenue.toFixed(2)}</p>
                    <p className="text-sm text-gray-500">Revenue</p>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </motion.div>
      </div>
    </div>
  )
}

export default AdminDashboard

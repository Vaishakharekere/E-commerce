import { motion } from 'framer-motion'
import { Package, Truck, CheckCircle, Clock } from 'lucide-react'
import { formatPrice, formatDate } from '../utils/format'

const Orders = () => {
  // Mock orders data
  const orders = [
    {
      id: 'ORD-001',
      date: '2024-01-15',
      status: 'delivered',
      total: 299.97,
      items: [
        { name: 'Wireless Bluetooth Headphones', quantity: 1, price: 99.99 },
        { name: 'Smart Fitness Watch', quantity: 1, price: 199.99 }
      ]
    },
    {
      id: 'ORD-002',
      date: '2024-01-10',
      status: 'shipped',
      total: 149.99,
      items: [
        { name: 'Smart Fitness Watch', quantity: 1, price: 149.99 }
      ]
    },
    {
      id: 'ORD-003',
      date: '2024-01-05',
      status: 'processing',
      total: 74.98,
      items: [
        { name: 'Organic Cotton T-Shirt', quantity: 2, price: 29.99 },
        { name: 'Stainless Steel Water Bottle', quantity: 1, price: 24.99 }
      ]
    }
  ]

  const getStatusIcon = (status) => {
    switch (status) {
      case 'delivered':
        return <CheckCircle className="w-5 h-5 text-green-600" />
      case 'shipped':
        return <Truck className="w-5 h-5 text-blue-600" />
      case 'processing':
        return <Clock className="w-5 h-5 text-yellow-600" />
      default:
        return <Package className="w-5 h-5 text-gray-600" />
    }
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'delivered':
        return 'bg-green-100 text-green-800'
      case 'shipped':
        return 'bg-blue-100 text-blue-800'
      case 'processing':
        return 'bg-yellow-100 text-yellow-800'
      default:
        return 'bg-gray-100 text-gray-800'
    }
  }

  const getStatusText = (status) => {
    switch (status) {
      case 'delivered':
        return 'Delivered'
      case 'shipped':
        return 'Shipped'
      case 'processing':
        return 'Processing'
      default:
        return 'Unknown'
    }
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900">My Orders</h1>
          <p className="text-gray-600">Track your order history and current shipments</p>
        </div>

        <div className="space-y-6">
          {orders.map((order, index) => (
            <motion.div
              key={order.id}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: index * 0.1 }}
              className="card"
            >
              <div className="p-6">
                {/* Order Header */}
                <div className="flex items-center justify-between mb-4">
                  <div className="flex items-center space-x-4">
                    <div className="flex items-center space-x-2">
                      {getStatusIcon(order.status)}
                      <span className={`px-2 py-1 rounded-full text-xs font-medium ${getStatusColor(order.status)}`}>
                        {getStatusText(order.status)}
                      </span>
                    </div>
                    <div>
                      <h3 className="text-lg font-semibold text-gray-900">{order.id}</h3>
                      <p className="text-sm text-gray-500">Placed on {formatDate(order.date)}</p>
                    </div>
                  </div>
                  <div className="text-right">
                    <p className="text-lg font-bold text-primary-600">{formatPrice(order.total)}</p>
                    <p className="text-sm text-gray-500">{order.items.length} item(s)</p>
                  </div>
                </div>

                {/* Order Items */}
                <div className="border-t border-gray-200 pt-4">
                  <div className="space-y-3">
                    {order.items.map((item, itemIndex) => (
                      <div key={itemIndex} className="flex items-center justify-between">
                        <div className="flex items-center space-x-3">
                          <div className="w-10 h-10 bg-gray-200 rounded flex items-center justify-center">
                            <Package className="w-5 h-5 text-gray-500" />
                          </div>
                          <div>
                            <p className="font-medium text-gray-900">{item.name}</p>
                            <p className="text-sm text-gray-500">Qty: {item.quantity}</p>
                          </div>
                        </div>
                        <span className="font-medium text-gray-900">{formatPrice(item.price * item.quantity)}</span>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Order Actions */}
                <div className="border-t border-gray-200 pt-4 mt-4">
                  <div className="flex items-center justify-between">
                    <div className="flex space-x-3">
                      <button className="text-primary-600 hover:text-primary-700 text-sm font-medium">
                        View Details
                      </button>
                      <button className="text-primary-600 hover:text-primary-700 text-sm font-medium">
                        Track Order
                      </button>
                      {order.status === 'delivered' && (
                        <button className="text-primary-600 hover:text-primary-700 text-sm font-medium">
                          Write Review
                        </button>
                      )}
                    </div>
                    {order.status === 'delivered' && (
                      <button className="text-primary-600 hover:text-primary-700 text-sm font-medium">
                        Reorder
                      </button>
                    )}
                  </div>
                </div>
              </div>
            </motion.div>
          ))}

          {orders.length === 0 && (
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              className="card text-center py-12"
            >
              <div className="text-gray-400 mb-4">
                <Package className="w-16 h-16 mx-auto" />
              </div>
              <h3 className="text-lg font-medium text-gray-600 mb-2">No orders yet</h3>
              <p className="text-gray-500 mb-6">Start shopping to see your order history here.</p>
              <button className="bg-primary-600 text-white px-6 py-3 rounded-lg font-medium hover:bg-primary-700 transition-colors">
                Start Shopping
              </button>
            </motion.div>
          )}
        </div>
      </div>
    </div>
  )
}

export default Orders

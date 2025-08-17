import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useCart } from '../contexts/CartContext'
import { ShoppingCart, Heart, Star, Eye } from 'lucide-react'
import { motion } from 'framer-motion'
import { formatPrice, calculateDiscount } from '../utils/format'

const ProductCard = ({ product, viewMode = 'grid' }) => {
  const [isHovered, setIsHovered] = useState(false)
  const { addToCart } = useCart()

  const handleAddToCart = (e) => {
    e.preventDefault()
    e.stopPropagation()
    addToCart(product.productId, 1)
  }

  const hasDiscount = product.discount && product.discount > 0
  const finalPrice = hasDiscount ? (product.price * (1 - product.discount / 100)) : product.price

  if (viewMode === 'list') {
    return (
      <motion.div
        whileHover={{ y: -2 }}
        className="card hover:shadow-medium transition-all duration-300"
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
      >
        <div className="flex">
          {/* Product Image */}
          <div className="w-48 h-48 flex-shrink-0">
            <img
              src={product.imageURL || 'https://via.placeholder.com/400x400?text=Product'}
              alt={product.name}
              className="w-full h-full object-cover rounded-l-lg"
            />
          </div>

          {/* Product Info */}
          <div className="flex-1 p-6">
            <div className="flex justify-between items-start mb-4">
              <div>
                <h3 className="text-xl font-semibold text-gray-900 mb-2">
                  <Link to={`/products/${product.productId}`} className="hover:text-primary-600 transition-colors">
                    {product.name}
                  </Link>
                </h3>
                <p className="text-gray-600 line-clamp-2 mb-3">{product.description}</p>
                <div className="flex items-center space-x-2 mb-3">
                  <div className="flex items-center">
                    {[...Array(5)].map((_, i) => (
                      <Star
                        key={i}
                        className={`w-4 h-4 ${
                          i < 4 ? 'text-yellow-400 fill-current' : 'text-gray-300'
                        }`}
                      />
                    ))}
                  </div>
                  <span className="text-sm text-gray-500">(24 reviews)</span>
                </div>
              </div>
            </div>

            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-3">
                {hasDiscount ? (
                  <>
                    <span className="text-2xl font-bold text-primary-600">
                      {formatPrice(finalPrice)}
                    </span>
                    <span className="text-lg text-gray-500 line-through">
                      {formatPrice(product.price)}
                    </span>
                    <span className="bg-red-100 text-red-800 text-sm font-medium px-2 py-1 rounded">
                      -{product.discount}%
                    </span>
                  </>
                ) : (
                  <span className="text-2xl font-bold text-primary-600">
                    {formatPrice(product.price)}
                  </span>
                )}
              </div>

              <div className="flex items-center space-x-2">
                <button
                  onClick={handleAddToCart}
                  disabled={product.quantity === 0}
                  className="btn btn-primary flex items-center space-x-2 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  <ShoppingCart className="w-4 h-4" />
                  <span>Add to Cart</span>
                </button>
                <button className="btn btn-outline p-2">
                  <Heart className="w-4 h-4" />
                </button>
                <Link to={`/products/${product.productId}`} className="btn btn-outline p-2">
                  <Eye className="w-4 h-4" />
                </Link>
              </div>
            </div>
          </div>
        </div>
      </motion.div>
    )
  }

  // Grid view (default)
  return (
    <motion.div
      whileHover={{ y: -5 }}
      className="card group overflow-hidden"
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      {/* Product Image */}
      <div className="relative overflow-hidden">
        <img
          src={product.imageURL || 'https://via.placeholder.com/400x400?text=Product'}
          alt={product.name}
          className="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-300"
        />
        
        {/* Discount Badge */}
        {hasDiscount && (
          <div className="absolute top-2 left-2 bg-red-500 text-white text-xs font-bold px-2 py-1 rounded">
            -{product.discount}%
          </div>
        )}

        {/* Quick Actions */}
        <div className={`absolute top-2 right-2 flex flex-col space-y-2 transition-all duration-300 ${
          isHovered ? 'opacity-100 translate-x-0' : 'opacity-0 translate-x-4'
        }`}>
          <button className="w-8 h-8 bg-white rounded-full shadow-md flex items-center justify-center hover:bg-primary-50 transition-colors">
            <Heart className="w-4 h-4 text-gray-600" />
          </button>
          <Link
            to={`/products/${product.productId}`}
            className="w-8 h-8 bg-white rounded-full shadow-md flex items-center justify-center hover:bg-primary-50 transition-colors"
          >
            <Eye className="w-4 h-4 text-gray-600" />
          </Link>
        </div>

        {/* Stock Status */}
        <div className="absolute bottom-2 left-2">
          {product.quantity > 0 ? (
            <span className="bg-green-100 text-green-800 text-xs font-medium px-2 py-1 rounded">
              In Stock
            </span>
          ) : (
            <span className="bg-red-100 text-red-800 text-xs font-medium px-2 py-1 rounded">
              Out of Stock
            </span>
          )}
        </div>
      </div>

      {/* Product Info */}
      <div className="p-4">
        <Link to={`/products/${product.productId}`}>
          <h3 className="font-semibold text-gray-900 mb-2 group-hover:text-primary-600 transition-colors line-clamp-2">
            {product.name}
          </h3>
        </Link>
        
        <p className="text-sm text-gray-600 mb-3 line-clamp-2">
          {product.description}
        </p>

        {/* Rating */}
        <div className="flex items-center space-x-2 mb-3">
          <div className="flex items-center">
            {[...Array(5)].map((_, i) => (
              <Star
                key={i}
                className={`w-4 h-4 ${
                  i < 4 ? 'text-yellow-400 fill-current' : 'text-gray-300'
                }`}
              />
            ))}
          </div>
          <span className="text-sm text-gray-500">(24)</span>
        </div>

        {/* Price */}
        <div className="flex items-center justify-between mb-4">
          <div className="flex items-center space-x-2">
            {hasDiscount ? (
              <>
                <span className="text-lg font-bold text-primary-600">
                  {formatPrice(finalPrice)}
                </span>
                <span className="text-sm text-gray-500 line-through">
                  {formatPrice(product.price)}
                </span>
              </>
            ) : (
              <span className="text-lg font-bold text-primary-600">
                {formatPrice(product.price)}
              </span>
            )}
          </div>
        </div>

        {/* Add to Cart Button */}
        <button
          onClick={handleAddToCart}
          disabled={product.quantity === 0}
          className="w-full btn btn-primary flex items-center justify-center space-x-2 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <ShoppingCart className="w-4 h-4" />
          <span>{product.quantity > 0 ? 'Add to Cart' : 'Out of Stock'}</span>
        </button>
      </div>
    </motion.div>
  )
}

export default ProductCard

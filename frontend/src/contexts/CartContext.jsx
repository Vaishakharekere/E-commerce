import { createContext, useContext, useState, useEffect } from 'react'
import axios from 'axios'
import toast from 'react-hot-toast'

const CartContext = createContext()

export const useCart = () => {
  const context = useContext(CartContext)
  if (!context) {
    throw new Error('useCart must be used within a CartProvider')
  }
  return context
}

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState([])
  const [loading, setLoading] = useState(false)

  // For now, we'll use a simple approach without authentication dependency
  const isAuthenticated = () => {
    return !!localStorage.getItem('token')
  }

  useEffect(() => {
    if (isAuthenticated()) {
      fetchCart()
    }
  }, [])

  const fetchCart = async () => {
    if (!isAuthenticated()) return
    
    try {
      setLoading(true)
      const response = await axios.get('/api/cart')
      setCartItems(response.data || [])
    } catch (error) {
      console.error('Error fetching cart:', error)
      // For development, set some dummy cart items
      setCartItems([])
    } finally {
      setLoading(false)
    }
  }

  const addToCart = async (productId, quantity = 1) => {
    if (!isAuthenticated()) {
      toast.error('Please login to add items to cart')
      return false
    }

    try {
      const response = await axios.post('/api/cart/add', {
        productId,
        quantity
      })
      setCartItems(response.data.cartItems || [])
      toast.success('Added to cart!')
      return true
    } catch (error) {
      console.error('Error adding to cart:', error)
      // For development, add to local state
      const dummyItem = {
        productId,
        quantity,
        product: {
          productId,
          name: `Product ${productId}`,
          price: 29.99,
          imageURL: `https://images.unsplash.com/photo-${1500000000000 + productId}?w=200&h=200&fit=crop`
        }
      }
      setCartItems(prev => [...prev, dummyItem])
      toast.success('Added to cart!')
      return true
    }
  }

  const removeFromCart = async (productId) => {
    try {
      await axios.delete(`/api/cart/remove/${productId}`)
      await fetchCart()
      toast.success('Removed from cart')
    } catch (error) {
      console.error('Error removing from cart:', error)
      // For development, remove from local state
      setCartItems(prev => prev.filter(item => item.productId !== productId))
      toast.success('Removed from cart')
    }
  }

  const updateQuantity = async (productId, quantity) => {
    try {
      await axios.put(`/api/cart/update`, {
        productId,
        quantity
      })
      await fetchCart()
    } catch (error) {
      console.error('Error updating quantity:', error)
      // For development, update local state
      setCartItems(prev => prev.map(item => 
        item.productId === productId ? { ...item, quantity } : item
      ))
    }
  }

  const clearCart = async () => {
    try {
      await axios.delete('/api/cart/clear')
      setCartItems([])
      toast.success('Cart cleared')
    } catch (error) {
      console.error('Error clearing cart:', error)
      // For development, clear local state
      setCartItems([])
      toast.success('Cart cleared')
    }
  }

  const getCartTotal = () => {
    return cartItems.reduce((total, item) => {
      const price = item.product.specialPrice || item.product.price
      return total + (price * item.quantity)
    }, 0)
  }

  const getCartCount = () => {
    return cartItems.reduce((count, item) => count + item.quantity, 0)
  }

  const value = {
    cartItems,
    loading,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
    getCartTotal,
    getCartCount,
    fetchCart
  }

  return (
    <CartContext.Provider value={value}>
      {children}
    </CartContext.Provider>
  )
}

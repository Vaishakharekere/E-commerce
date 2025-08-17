import { createContext, useContext, useState, useEffect } from 'react'
import axios from 'axios'

const ProductContext = createContext()

export const useProduct = () => {
  const context = useContext(ProductContext)
  if (!context) {
    throw new Error('useProduct must be used within a ProductProvider')
  }
  return context
}

export const ProductProvider = ({ children }) => {
  const [products, setProducts] = useState([])
  const [categories, setCategories] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchProducts()
    fetchCategories()
  }, [])

  const fetchProducts = async () => {
    try {
      setLoading(true)
      setError(null)
      const response = await axios.get('/api/products')
      setProducts(response.data)
    } catch (error) {
      console.error('Error fetching products:', error)
      setError('Failed to fetch products')
      // Fallback to dummy data for development
      setProducts(getDummyProducts())
    } finally {
      setLoading(false)
    }
  }

  const fetchCategories = async () => {
    try {
      const response = await axios.get('/api/categories')
      setCategories(response.data)
    } catch (error) {
      console.error('Error fetching categories:', error)
      // Fallback to dummy categories
      setCategories(getDummyCategories())
    }
  }

  const getProductById = (id) => {
    return products.find(product => product.productId === parseInt(id))
  }

  const getProductsByCategory = (categoryId) => {
    return products.filter(product => product.category?.categoryId === categoryId)
  }

  const searchProducts = (query) => {
    if (!query) return products
    return products.filter(product => 
      product.name.toLowerCase().includes(query.toLowerCase()) ||
      product.description.toLowerCase().includes(query.toLowerCase())
    )
  }

  const filterProducts = (filters) => {
    let filtered = [...products]

    if (filters.category) {
      filtered = filtered.filter(product => product.category?.categoryId === filters.category)
    }

    if (filters.minPrice !== undefined) {
      filtered = filtered.filter(product => product.price >= filters.minPrice)
    }

    if (filters.maxPrice !== undefined) {
      filtered = filtered.filter(product => product.price <= filters.maxPrice)
    }

    if (filters.sortBy) {
      switch (filters.sortBy) {
        case 'price-low':
          filtered.sort((a, b) => a.price - b.price)
          break
        case 'price-high':
          filtered.sort((a, b) => b.price - a.price)
          break
        case 'name':
          filtered.sort((a, b) => a.name.localeCompare(b.name))
          break
        default:
          break
      }
    }

    return filtered
  }

  // Dummy data for development
  const getDummyProducts = () => [
    {
      productId: 1,
      name: "Wireless Bluetooth Headphones",
      description: "High-quality wireless headphones with noise cancellation and long battery life.",
      price: 99.99,
      specialPrice: 79.99,
      discount: 20,
      quantity: 50,
      imageURL: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&h=400&fit=crop",
      category: { categoryId: 1, name: "Electronics" }
    },
    {
      productId: 2,
      name: "Smart Fitness Watch",
      description: "Advanced fitness tracking with heart rate monitor and GPS.",
      price: 199.99,
      specialPrice: 149.99,
      discount: 25,
      quantity: 30,
      imageURL: "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400&h=400&fit=crop",
      category: { categoryId: 1, name: "Electronics" }
    },
    {
      productId: 3,
      name: "Organic Cotton T-Shirt",
      description: "Comfortable and sustainable cotton t-shirt in various colors.",
      price: 29.99,
      quantity: 100,
      imageURL: "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&h=400&fit=crop",
      category: { categoryId: 2, name: "Clothing" }
    },
    {
      productId: 4,
      name: "Stainless Steel Water Bottle",
      description: "Eco-friendly water bottle that keeps drinks cold for 24 hours.",
      price: 24.99,
      quantity: 75,
      imageURL: "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=400&h=400&fit=crop",
      category: { categoryId: 3, name: "Home & Garden" }
    },
    {
      productId: 5,
      name: "Professional Camera Lens",
      description: "High-quality camera lens for professional photography.",
      price: 599.99,
      specialPrice: 499.99,
      discount: 17,
      quantity: 15,
      imageURL: "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=400&h=400&fit=crop",
      category: { categoryId: 1, name: "Electronics" }
    },
    {
      productId: 6,
      name: "Leather Wallet",
      description: "Genuine leather wallet with multiple card slots and coin pocket.",
      price: 49.99,
      quantity: 60,
      imageURL: "https://images.unsplash.com/photo-1627123424574-724758594e93?w=400&h=400&fit=crop",
      category: { categoryId: 2, name: "Clothing" }
    }
  ]

  const getDummyCategories = () => [
    { categoryId: 1, name: "Electronics" },
    { categoryId: 2, name: "Clothing" },
    { categoryId: 3, name: "Home & Garden" },
    { categoryId: 4, name: "Sports & Outdoors" },
    { categoryId: 5, name: "Books & Media" }
  ]

  const value = {
    products,
    categories,
    loading,
    error,
    fetchProducts,
    fetchCategories,
    getProductById,
    getProductsByCategory,
    searchProducts,
    filterProducts
  }

  return (
    <ProductContext.Provider value={value}>
      {children}
    </ProductContext.Provider>
  )
}

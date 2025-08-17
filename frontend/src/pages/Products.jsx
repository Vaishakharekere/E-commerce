import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { Search, Filter, Grid, List, Star, ShoppingCart, Heart, Eye } from 'lucide-react';
import { useProduct } from '../contexts/ProductContext';
import { useCart } from '../contexts/CartContext';
import { formatPrice } from '../utils/format';

const Products = () => {
  const { products } = useProduct();
  const { addToCart } = useCart();

  const [searchTerm, setSearchTerm] = useState('');
  const [filteredProducts, setFilteredProducts] = useState(products);
  const [viewMode, setViewMode] = useState('grid');

  useEffect(() => {
    setFilteredProducts(
      products.filter(product =>
        product.name.toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
  }, [searchTerm, products]);

  return (
    <div className="p-6">
      {/* Top controls */}
      <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-6">
        {/* Search bar */}
        <div className="relative w-full md:w-1/3">
          <Search className="absolute left-3 top-3 text-gray-400 w-5 h-5" />
          <input
            type="text"
            placeholder="Search products..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full pl-10 pr-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>

        {/* View toggle buttons */}
        <div className="flex gap-2">
          <button
            onClick={() => setViewMode('grid')}
            className={`p-2 rounded-lg transition-all duration-300 ${
              viewMode === 'grid'
                ? 'bg-blue-500 text-white shadow-lg'
                : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
            }`}
          >
            <Grid className="w-5 h-5" /> {/* ✅ Corrected */}
          </button>
          <button
            onClick={() => setViewMode('list')}
            className={`p-2 rounded-lg transition-all duration-300 ${
              viewMode === 'list'
                ? 'bg-blue-500 text-white shadow-lg'
                : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
            }`}
          >
            <List className="w-5 h-5" />
          </button>
        </div>
      </div>

      {/* Product grid/list */}
      <div
        className={
          viewMode === 'grid'
            ? 'grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6'
            : 'flex flex-col gap-4'
        }
      >
        {filteredProducts.map((product, index) => (
          <motion.div
            key={product.id}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.05 }}
            className="bg-white rounded-2xl shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300"
          >
            <div className="relative">
              <img
                src={product.image}
                alt={product.name}
                className="w-full h-48 object-cover"
              />
              <div className="absolute top-2 right-2 flex flex-col gap-2">
                <button className="p-2 bg-white rounded-full shadow hover:bg-gray-100">
                  <Heart className="w-4 h-4 text-gray-600" />
                </button>
                <button className="p-2 bg-white rounded-full shadow hover:bg-gray-100">
                  <Eye className="w-4 h-4 text-gray-600" />
                </button>
              </div>
            </div>
            <div className="p-4">
              <h3 className="font-semibold text-lg">{product.name}</h3>
              <p className="text-gray-500 text-sm">{product.category}</p>
              <div className="flex items-center mt-2">
                <Star className="w-4 h-4 text-yellow-400" />
                <span className="ml-1 text-sm text-gray-600">
                  {product.rating}
                </span>
              </div>
              <div className="flex items-center justify-between mt-4">
                <span className="text-xl font-bold text-blue-600">
                  {formatPrice(product.price)}
                </span>
                <button
                  onClick={() => addToCart(product)}
                  className="flex items-center gap-2 px-3 py-1 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
                >
                  <ShoppingCart className="w-4 h-4" /> Add
                </button>
              </div>
            </div>
          </motion.div>
        ))}
      </div>
    </div>
  );
};

export default Products;

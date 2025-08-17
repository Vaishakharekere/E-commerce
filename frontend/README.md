# EStore - Modern E-Commerce Frontend

A beautiful, modern e-commerce frontend built with React, Vite, TailwindCSS, and Framer Motion. This application provides a professional shopping experience similar to Amazon/Flipkart with full authentication, product management, shopping cart, and checkout functionality.

## 🚀 Features

### Authentication
- **Login Page**: Beautiful login form with email/password
- **Registration Page**: User signup with validation
- **JWT Integration**: Secure authentication with backend
- **Protected Routes**: Role-based access control

### E-Commerce Features
- **Home Page**: Hero section, featured products, and categories
- **Product Listing**: Search, filter, and sort products
- **Product Details**: Image carousel, reviews, and add to cart
- **Shopping Cart**: Item management and quantity controls
- **Checkout Process**: Multi-step checkout with address and payment
- **User Dashboard**: Profile management and order history
- **Admin Panel**: Store management and analytics

### UI/UX Features
- **Responsive Design**: Mobile-first approach for all devices
- **Modern Design**: Clean, professional interface like Amazon
- **Smooth Animations**: Framer Motion for delightful interactions
- **Toast Notifications**: User feedback for all actions
- **Loading States**: Skeleton loaders and spinners

## 🛠️ Tech Stack

- **Frontend Framework**: React 18 with Hooks
- **Build Tool**: Vite
- **Styling**: TailwindCSS
- **Icons**: Lucide React
- **Animations**: Framer Motion
- **HTTP Client**: Axios
- **Notifications**: React Hot Toast
- **Routing**: React Router DOM

## 📦 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Environment Setup**
   Create a `.env` file in the root directory:
   ```env
   VITE_API_URL=http://localhost:8080
   ```

4. **Start Development Server**
   ```bash
   npm run dev
   ```

The application will open at `http://localhost:3000`

## 🔧 Configuration

### Backend Integration
The frontend is configured to work with a Spring Boot backend running on port 8080. Update the proxy configuration in `vite.config.js` if your backend runs on a different port.

### API Endpoints
The application expects the following backend endpoints:
- `/api/auth/login` - User authentication
- `/api/auth/register` - User registration
- `/api/products` - Product listing
- `/api/cart/*` - Cart management
- `/api/orders/*` - Order management

## 📱 Pages & Components

### Core Pages
- **Home** (`/`) - Landing page with featured products
- **Products** (`/products`) - Product catalog with filters
- **Product Detail** (`/products/:id`) - Individual product view
- **Cart** (`/cart`) - Shopping cart management
- **Checkout** (`/checkout`) - Order completion process
- **Login** (`/login`) - User authentication
- **Register** (`/register`) - User registration
- **Profile** (`/profile`) - User account management
- **Orders** (`/orders`) - Order history
- **Admin** (`/admin`) - Admin dashboard

### Key Components
- **Navbar** - Navigation with search, cart, and user menu
- **Footer** - Site links and company information
- **ProductCard** - Product display in grids and lists
- **ProtectedRoute** - Authentication guard for protected pages
- **AdminRoute** - Admin-only access control

## 🎨 Styling

### TailwindCSS Classes
The application uses custom TailwindCSS classes defined in `tailwind.config.js`:
- **Primary Colors**: Blue-based color scheme
- **Secondary Colors**: Gray variations
- **Accent Colors**: Yellow highlights
- **Custom Shadows**: Soft, medium, and large shadows
- **Animations**: Fade-in, slide-up, and bounce effects

### Component Styling
- **Cards**: Consistent card design with shadows and borders
- **Buttons**: Primary, secondary, and outline button styles
- **Forms**: Clean form inputs with focus states
- **Responsive Grid**: Mobile-first responsive layouts

## 🔐 Authentication Flow

1. **Registration**: User creates account with email/password
2. **Login**: User authenticates and receives JWT token
3. **Token Storage**: JWT stored securely in localStorage
4. **Protected Routes**: Authentication required for cart, checkout, profile
5. **Admin Access**: Role-based access control for admin features

## 🛒 Shopping Flow

1. **Browse Products**: View products with search and filters
2. **Product Details**: View detailed product information
3. **Add to Cart**: Add products to shopping cart
4. **Cart Management**: Review and modify cart items
5. **Checkout**: Complete purchase with shipping and payment
6. **Order Confirmation**: Receive order confirmation

## 📱 Responsive Design

The application is fully responsive with breakpoints:
- **Mobile**: < 768px - Single column layouts
- **Tablet**: 768px - 1024px - Two column layouts
- **Desktop**: > 1024px - Full multi-column layouts

## 🚀 Performance Features

- **Code Splitting**: Automatic route-based code splitting
- **Lazy Loading**: Components loaded on demand
- **Optimized Builds**: Vite optimizations for production
- **Image Optimization**: Responsive images with proper sizing

## 🧪 Development

### Available Scripts
```bash
npm run dev          # Start development server
npm run build        # Build for production
npm run preview      # Preview production build
npm run lint         # Run ESLint
```

### Code Structure
```
src/
├── components/      # Reusable UI components
├── contexts/        # React context providers
├── pages/          # Page components
├── utils/          # Utility functions
├── App.jsx         # Main application component
└── main.jsx        # Application entry point
```

## 🔧 Customization

### Adding New Pages
1. Create page component in `src/pages/`
2. Add route in `src/App.jsx`
3. Update navigation if needed

### Adding New Components
1. Create component in `src/components/`
2. Export as default
3. Import and use in pages

### Styling Changes
1. Modify `tailwind.config.js` for theme changes
2. Update `src/index.css` for global styles
3. Use TailwindCSS classes for component styling

## 🚀 Deployment

### Build for Production
```bash
npm run build
```

### Deploy to Vercel/Netlify
1. Connect your repository
2. Set build command: `npm run build`
3. Set output directory: `dist`
4. Deploy!

### Environment Variables
Set production environment variables:
- `VITE_API_URL` - Your production backend URL

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Make changes
4. Test thoroughly
5. Submit pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the documentation
- Review the code examples

## 🔮 Future Enhancements

- **PWA Support**: Offline functionality and app-like experience
- **Advanced Search**: Elasticsearch integration
- **Payment Gateway**: Stripe/PayPal integration
- **Real-time Chat**: Customer support chat
- **Analytics**: User behavior tracking
- **Multi-language**: Internationalization support

---

Built with ❤️ using modern web technologies

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // 外星流光色系
        alien: {
          cyan: '#00F5FF',
          magenta: '#FF00FF',
          lime: '#39FF14',
          violet: '#8B5CF6',
          electric: '#7C3AED',
          plasma: '#06FFA5',
          void: '#0A0A0F',
          deep: '#050508',
          surface: '#12121A',
          glass: 'rgba(18, 18, 26, 0.8)',
        },
        neon: {
          blue: '#00D9FF',
          pink: '#FF006E',
          green: '#00FF88',
          purple: '#B829DD',
          yellow: '#FFE600',
        }
      },
      fontFamily: {
        display: ['Orbitron', 'system-ui', 'sans-serif'],
        mono: ['JetBrains Mono', 'monospace'],
      },
      animation: {
        'pulse-glow': 'pulseGlow 2s ease-in-out infinite',
        'float': 'float 6s ease-in-out infinite',
        'scan': 'scan 4s linear infinite',
        'flicker': 'flicker 3s linear infinite',
        'warp': 'warp 0.5s ease-out',
        'glitch': 'glitch 1s linear infinite',
      },
      keyframes: {
        pulseGlow: {
          '0%, 100%': { boxShadow: '0 0 20px rgba(0, 245, 255, 0.5), 0 0 40px rgba(0, 245, 255, 0.3)' },
          '50%': { boxShadow: '0 0 30px rgba(0, 245, 255, 0.8), 0 0 60px rgba(0, 245, 255, 0.5)' },
        },
        float: {
          '0%, 100%': { transform: 'translateY(0px)' },
          '50%': { transform: 'translateY(-20px)' },
        },
        scan: {
          '0%': { transform: 'translateY(-100%)' },
          '100%': { transform: 'translateY(100%)' },
        },
        flicker: {
          '0%, 100%': { opacity: '1' },
          '50%': { opacity: '0.8' },
        },
        warp: {
          '0%': { transform: 'scale(0.8)', opacity: '0' },
          '100%': { transform: 'scale(1)', opacity: '1' },
        },
        glitch: {
          '0%, 100%': { transform: 'translate(0)' },
          '20%': { transform: 'translate(-2px, 2px)' },
          '40%': { transform: 'translate(-2px, -2px)' },
          '60%': { transform: 'translate(2px, 2px)' },
          '80%': { transform: 'translate(2px, -2px)' },
        },
      },
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic': 'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
        'alien-gradient': 'linear-gradient(135deg, #00F5FF 0%, #FF00FF 50%, #39FF14 100%)',
        'void-gradient': 'radial-gradient(ellipse at center, #1a1a2e 0%, #0A0A0F 100%)',
      },
    },
  },
  plugins: [],
}

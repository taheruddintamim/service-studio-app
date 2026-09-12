const fs = require('fs');

// High-fidelity SVG recreation of the geometric cyan/electric blue rounded triangle monogram
const svg = `<?xml version="1.0" encoding="UTF-8"?>
<svg width="512" height="512" viewBox="0 0 512 512" fill="none" xmlns="http://www.w3.org/2000/svg">
  <defs>
    <linearGradient id="blueGradient" x1="100" y1="50" x2="400" y2="450" gradientUnits="userSpaceOnUse">
      <stop offset="0%" stop-color="#00A3FF" />
      <stop offset="100%" stop-color="#0066FF" />
    </linearGradient>
    <filter id="glow" x="-20%" y="-20%" width="140%" height="140%">
      <feDropShadow dx="0" dy="0" stdDeviation="12" flood-color="#0084FF" flood-opacity="0.4"/>
    </filter>
  </defs>

  <!-- Group with stroke style matching the monogram -->
  <g stroke="url(#blueGradient)" stroke-width="32" stroke-linecap="round" stroke-linejoin="round">
    <!-- Left vertical 't' segment -->
    <!-- Crossbar -->
    <line x1="68" y1="168" x2="184" y2="168" />
    <!-- Vertical stem with bottom curve -->
    <path d="M 126 102 L 126 390 A 40 40 0 0 0 196 426 L 198 426" />

    <!-- Top-Right angled segment with crossbar -->
    <!-- Top corner hook and angled side -->
    <path d="M 144 78 A 38 38 0 0 1 188 44 L 370 198 A 38 38 0 0 1 414 240" />
    <!-- Rotated crossbar on top-right side -->
    <line x1="316" y1="250" x2="406" y2="158" />

    <!-- Bottom angled segment with crossbar -->
    <path d="M 414 262 L 418 300 A 38 38 0 0 1 388 348 L 204 438" />
    <!-- Rotated crossbar on bottom side -->
    <line x1="230" y1="346" x2="298" y2="466" />
  </g>
</svg>
`;

fs.writeFileSync('/tmp/logo.svg', svg);
console.log('SVG written to /tmp/logo.svg');

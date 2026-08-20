import nextCoreWebVitals from 'eslint-config-next/core-web-vitals';
import nextTypescript from 'eslint-config-next/typescript';
import prettierRecommended from 'eslint-plugin-prettier/recommended';

const eslintConfig = [
  { ignores: ['.next/**', 'node_modules/**', 'target/**', 'node/**'] },
  ...nextCoreWebVitals,
  ...nextTypescript,
  prettierRecommended,
];

export default eslintConfig;

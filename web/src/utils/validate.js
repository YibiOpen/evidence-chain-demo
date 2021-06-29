/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}
export function isDecimal(path) {
  return /\b0(\.\d{1,2})\b/.test(path);
}

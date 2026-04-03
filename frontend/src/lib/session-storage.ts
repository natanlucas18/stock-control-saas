import { SessionData } from "../types/session-data"

const KEY = "app_session"

export function saveSession(data: SessionData) {
  sessionStorage.setItem(KEY, JSON.stringify(data))
}

export function getSession() {
  const raw = sessionStorage.getItem(KEY)
  if (!raw) return null
  return JSON.parse(raw)
}

export function clearSession() {
  sessionStorage.removeItem(KEY)
}

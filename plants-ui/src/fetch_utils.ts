const HOST = process.env.REACT_APP_BACKEND_HOST || "localhost:8085"
const GATEWAY_URL = `http://${HOST}/api`

export const genericFetch = (endpoint: string, callback?: (data: any) => void, method?: string, body?: any) => {
    return fetch(`${GATEWAY_URL}/${endpoint}`, {
        method: method || "GET",
        body: body ? JSON.stringify(body) : undefined,
        headers: {
            "Content-Type": "application/json"
        }
    }).then((response) => response.json()).then(callback)
    .catch((err) => console.log(`Error while calling ${GATEWAY_URL}/${endpoint}: ${err}`))
}
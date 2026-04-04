function getTokenExp(token: string): number {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.exp;
}
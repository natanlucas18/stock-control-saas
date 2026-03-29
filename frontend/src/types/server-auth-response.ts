interface ServerAuthResponse {
    success: boolean;
    data: {
        userId: number;
        userName: string;
        userEmail: string;
        userRoles: string[];
        accessToken: string;
        refreshToken: string;
    };
    errors: any;
}